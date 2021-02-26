package org.keycloak.protocol.oidc.federation.common.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadataPolicy {

    @JsonProperty("openid_relying_party")
    private RPMetadataPolicy rpPolicy;
    
    @JsonProperty("openid_provider")
    private OPMetadataPolicy opPolicy;
    
    public MetadataPolicy() {
        
    }

    public MetadataPolicy(RPMetadataPolicy rpPolicy) {
        this.rpPolicy =rpPolicy;
    }
    
    public MetadataPolicy(OPMetadataPolicy opPolicy) {
        this.opPolicy =opPolicy;
    }

    public RPMetadataPolicy getRpPolicy() {
        return rpPolicy;
    }

    public void setRpPolicy(RPMetadataPolicy rpPolicy) {
        this.rpPolicy = rpPolicy;
    }

	public OPMetadataPolicy getOpPolicy() {
		return opPolicy;
	}

	public void setOpPolicy(OPMetadataPolicy opPolicy) {
		this.opPolicy = opPolicy;
	}
    
    
}
