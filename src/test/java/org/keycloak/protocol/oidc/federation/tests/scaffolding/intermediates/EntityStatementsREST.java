package org.keycloak.protocol.oidc.federation.tests.scaffolding.intermediates;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.keycloak.common.util.Time;
import org.keycloak.jose.jwk.JSONWebKeySet;
import org.keycloak.jose.jwk.JWK;
import org.keycloak.protocol.oidc.federation.common.beans.EntityStatement;
import org.keycloak.protocol.oidc.federation.common.beans.FederationEntity;
import org.keycloak.protocol.oidc.federation.common.beans.Metadata;
import org.keycloak.protocol.oidc.federation.common.beans.MetadataPolicy;
import org.keycloak.protocol.oidc.federation.common.beans.OPMetadataPolicy;
import org.keycloak.protocol.oidc.federation.common.exceptions.UnparsableException;
import org.keycloak.protocol.oidc.federation.common.helpers.FedUtils;
import org.keycloak.protocol.oidc.federation.common.processes.TrustChainProcessor;
import org.keycloak.protocol.oidc.federation.tests.scaffolding.commons.Toolbox;
import org.keycloak.protocol.oidc.federation.tests.scaffolding.configuration.YamlConfiguration;
import org.keycloak.protocol.oidc.federation.tests.scaffolding.configuration.Configuration.IntermediateEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import antlr.build.Tool;

@Path("/")
public class EntityStatementsREST {

    private Logger logger = LoggerFactory.getLogger(EntityStatementsREST.class);
    
	public static final Long ENTITY_EXPIRES_AFTER_SEC = 86400L; //24 hours
	public static final List<String> CLIENT_REGISTRATION_TYPES_SUPPORTED = Arrays.asList("automatic", "explicit");
	
	private TrustChainProcessor trustChainProcessor = new TrustChainProcessor();
	
	
	
	@GET
    @Path("{issuer}/.well-known/openid-federation")
	@Produces("application/jose")
	public String wellKnown(@PathParam("issuer") String issuer) throws JOSEException, ParseException, MalformedURLException, UnparsableException, IOException {
	
	    final String host = YamlConfiguration.getConfig().getHost().endsWith("/") ? YamlConfiguration.getConfig().getHost() : YamlConfiguration.getConfig().getHost() + "/";
		
	    logger.info("Asking for the self-signed statement of: " + host+issuer);
		
		EntityStatement es = new EntityStatement();
		es.issuer(host+issuer).subject(host+issuer).issuedNow().nbf(Long.valueOf(Time.currentTime())).exp(Long.valueOf(Time.currentTime()) + ENTITY_EXPIRES_AFTER_SEC);
		IntermediateEntity entity = YamlConfiguration.getConfig().getEntities().get(issuer);
		if((entity != null) && (entity.getAuthorityHints() != null))
			es.setAuthorityHints(entity.getAuthorityHints().stream().map(iss -> host+iss).collect(Collectors.toList()));
		
		JSONWebKeySet jwks = getSigningKeyOf(issuer);
		es.setJwks(jwks);
		
		Metadata metadata = new Metadata();
		
		FederationEntity federationEntity = new FederationEntity();
		federationEntity.setFederationApiEndpoint(host + "fedapi");
		metadata.setFederationEntity(federationEntity);
			
        es.setMetadata(metadata);
        
		return Toolbox.signAndSerialize(es);
	}
	
	
	
	@GET
	@Path("fedapi")
	@Produces("application/jose")
	public String fedreg(@QueryParam("iss") String issuer, @QueryParam("sub") String subject) throws JOSEException, ParseException, MalformedURLException, UnparsableException, IOException {
	    
	    logger.info(String.format("Asking %s about: %s" , issuer, subject));
	    
		final String host = YamlConfiguration.getConfig().getHost().endsWith("/") ? YamlConfiguration.getConfig().getHost() : YamlConfiguration.getConfig().getHost() + "/";
		
		String issuerKey = issuer.substring(issuer.lastIndexOf("/")).replace("/", "");
		
		EntityStatement es = new EntityStatement();
		es.issuer(issuer).subject(subject).issuedNow().nbf(Long.valueOf(Time.currentTime())).exp(Long.valueOf(Time.currentTime()) + ENTITY_EXPIRES_AFTER_SEC);
		IntermediateEntity entityConf = YamlConfiguration.getConfig().getEntities().get(issuerKey);
		if(entityConf != null && entityConf.getAuthorityHints() != null)
			es.setAuthorityHints(YamlConfiguration.getConfig().getEntities().get(issuerKey).getAuthorityHints().stream().map(iss -> host+iss).collect(Collectors.toList()));
		
		
		JSONWebKeySet jwks = getSigningKeyOf(subject);
		es.setJwks(jwks);
		
		OPMetadataPolicy policy = new OPMetadataPolicy();
//		policy.setIssuer(new PolicyBuilder<String>().add(issuer).build());
		
        MetadataPolicy metadataPolicy = new MetadataPolicy();
        if(entityConf != null && entityConf.getMetadataPolicies() != null) {
            metadataPolicy.setRpPolicy(entityConf.getMetadataPolicies());
        }
        metadataPolicy.setOpPolicy(policy);

        es.setMetadataPolicy(metadataPolicy);
		
		return Toolbox.signAndSerialize(es);
	}
	
	
	private JSONWebKeySet getSigningKeyOf(String entityId) throws UnparsableException, MalformedURLException, IOException {
	    

	    if(entityId != null && entityId.startsWith("http")) { //it's a link, not an internal i.e. from keycloak
	        String encodedLeafES = FedUtils.getSelfSignedToken(entityId);
	        return trustChainProcessor.parse(encodedLeafES).getJwks();
	    }
	    else {
	        JSONWebKeySet jwks = new JSONWebKeySet();
	        jwks.setKeys(new JWK[] {Toolbox.getObjectMapper().readValue(Toolbox.getSigningKey().toPublicJWK().toJSONString(), JWK.class)});
	        return jwks;
	    }
	    
	}
	
	
	
	
}