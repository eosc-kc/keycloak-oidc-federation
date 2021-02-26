package org.keycloak.protocol.oidc.federation.common.beans;

import java.util.List;
import java.util.Map;

import org.keycloak.TokenCategory;
import org.keycloak.jose.jwk.JSONWebKeySet;
import org.keycloak.protocol.oidc.federation.common.beans.constraints.Constraints;
import org.keycloak.representations.JsonWebToken;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class EntityStatement extends JsonWebToken {

    @JsonProperty("authority_hints")
    protected List<String> authorityHints;
	
    protected JSONWebKeySet jwks;
    
    protected Metadata metadata;
    
    @JsonProperty("metadata_policy")
    protected MetadataPolicy metadataPolicy;
    
    @JsonInclude(Include.NON_EMPTY)
    protected Constraints constraints;
    
    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty("crit")
    protected List<String> critical;
    
    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty("policy_language_crit")
    protected List<String> policyLanguageCritical;
    
    @JsonInclude(Include.NON_EMPTY)
    @JsonProperty("trust_marks")
    protected List<TrustMark> trustMarks;
    
    
	public List<String> getAuthorityHints() {
		return authorityHints;
	}

	public void setAuthorityHints(List<String> authorityHints) {
		this.authorityHints = authorityHints;
	}

	public JSONWebKeySet getJwks() {
		return jwks;
	}

	public void setJwks(JSONWebKeySet jwks) {
		this.jwks = jwks;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}
    
    public MetadataPolicy getMetadataPolicy() {
        return metadataPolicy;
    }

    public void setMetadataPolicy(MetadataPolicy metadataPolicy) {
        this.metadataPolicy = metadataPolicy;
    }
    
    public Constraints getConstraints() {
        return constraints;
    }

    public void setConstraints(Constraints constraints) {
        this.constraints = constraints;
    }

    public List<String> getCritical() {
        return critical;
    }

    public void setCritical(List<String> critical) {
        this.critical = critical;
    }

    public List<String> getPolicyLanguageCritical() {
        return policyLanguageCritical;
    }

    public void setPolicyLanguageCritical(List<String> policyLanguageCritical) {
        this.policyLanguageCritical = policyLanguageCritical;
    }

    public List<TrustMark> getTrustMarks() {
        return trustMarks;
    }

    public void setTrustMarks(List<TrustMark> trustMarks) {
        this.trustMarks = trustMarks;
    }

    @Override
    public TokenCategory getCategory() {
        return TokenCategory.ACCESS; //treat it as an access token (use asymmetric crypto algorithms)
    }
    
	
    @JsonAnyGetter
    public Map<String, Object> getOtherClaims() {
        return otherClaims;
    }

    @JsonAnySetter
    public void setOtherClaims(String name, Object value) {
        otherClaims.put(name, value);
    }
    
}
