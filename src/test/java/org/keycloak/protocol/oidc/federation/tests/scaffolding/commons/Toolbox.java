package org.keycloak.protocol.oidc.federation.tests.scaffolding.commons;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.keycloak.protocol.oidc.federation.common.beans.EntityStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
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


public class Toolbox {
    
    private Logger logger = LoggerFactory.getLogger(Toolbox.class);
    
    private static ObjectMapper om;
    
    private static RSAKey signingKey;
    private static RSAKey encryptionKey;
    
    static {
        om = new ObjectMapper();
        om.setSerializationInclusion(Include.NON_NULL);
        try {
            signingKey = readKey("sig_rsa.key");
            encryptionKey = readKey("enc_rsa.key");
        }
        catch(Exception ex) {
            throw new RuntimeException("Please make sure that the resources folder contain the files 'sig_rsa.key' and 'enc_rsa.key'. Could not initialize test suites of this component");
        }
    }
    

    public static ObjectMapper getObjectMapper() {
        return om;
    }
    
    public static RSAKey getSigningKey() {
        return signingKey;
    }
    
    public static RSAKey getEncryptionKey() {
        return encryptionKey;
    }
    
    public static String signAndSerialize(EntityStatement es) throws JOSEException, JsonProcessingException, ParseException {
        JWSSigner signer = new RSASSASigner(signingKey);
        JWTClaimsSet claimsSet = JWTClaimsSet.parse(om.writeValueAsString(es));
        SignedJWT signedJWT = new SignedJWT(
            new JWSHeader.Builder(JWSAlgorithm.RS256).keyID(signingKey.getKeyID()).build(),
            claimsSet);
        signedJWT.sign(signer);
        return signedJWT.serialize();
    }
    
    
    
    private static RSAKey readKey(String resourceFileName) throws Exception {
        InputStream is = Toolbox.class.getClassLoader().getResourceAsStream(resourceFileName);
        String key = new BufferedReader(new InputStreamReader(is, Charset.defaultCharset())).lines().collect(Collectors.joining(System.lineSeparator()));
        return RSAKey.parse(key);
    }
    
    
    private String urlEncode(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
    }
    
    private String urlDecode(String url) throws UnsupportedEncodingException {
        return URLDecoder.decode(url, StandardCharsets.UTF_8.toString());
    }
    
    
}
