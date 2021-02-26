package org.keycloak.protocol.oidc.federation.op.model;

import java.io.Serializable;
import java.util.Set;

public class OIDCFedConfig implements Serializable {

    private static final long serialVersionUID = -189202639856637693L;

    private Set<String> authorityHints;
    private Set<String> trustAnchors;
    private String registrationType;
    private Integer expirationTime;
    
    
    public Set<String> getAuthorityHints() {
        return authorityHints;
    }
    public void setAuthorityHints(Set<String> authorityHints) {
        this.authorityHints = authorityHints;
    }
    
    public Set<String> getTrustAnchors() {
        return trustAnchors;
    }
    public void setTrustAnchors(Set<String> trustAnchors) {
        this.trustAnchors = trustAnchors;
    }
    public String getRegistrationType() {
        return registrationType;
    }
    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }
    public Integer getExpirationTime() {
        return expirationTime;
    }
    public void setExpirationTime(Integer expirationTime) {
        this.expirationTime = expirationTime;
    }
    
}
