package org.keycloak.protocol.oidc.federation.common.beans;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Metadata {
    
    @JsonProperty("openid_provider")
    private OPMetadata op;
    
    @JsonProperty("federation_entity")
    private FederationEntity federationEntity;
    
    @JsonProperty("openid_relying_party")
    private RPMetadata rp;

    public OPMetadata getOp() {
        return op;
    }

    public void setOp(OPMetadata op) {
        this.op = op;
    }

    public FederationEntity getFederationEntity() {
        return federationEntity;
    }

    public void setFederationEntity(FederationEntity federationEntity) {
        this.federationEntity = federationEntity;
    }

    public RPMetadata getRp() {
        return rp;
    }

    public void setRp(RPMetadata rp) {
        this.rp = rp;
    }

    
}
