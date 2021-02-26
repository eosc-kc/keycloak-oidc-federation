package org.keycloak.protocol.oidc.federation.rp.broker;

import static org.keycloak.common.util.UriUtils.checkUrl;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.keycloak.broker.oidc.OIDCIdentityProviderConfig;
import org.keycloak.common.enums.SslRequired;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.RealmModel;
import org.keycloak.util.JsonSerialization;

public class OIDCFedIdentityProviderConfig extends OIDCIdentityProviderConfig {
    
    public static final String CLIENT_REGISTRATION_TYPES = "clientRegistrationTypes";    
    public static final String ORGANIZATION_NAME = "organizationName";
    public static final String AUTHORITY_HINTS = "authorityHints";
    public static final String EXPIRED = "expired";
    public static final String TRUST_ANCHOR_IDS = "trustAnchorIds";
    public static final String OP_ENTITY_IDENTIFIER = "opEntityIdentifier";

    public OIDCFedIdentityProviderConfig() {
        super();
    }
    
    public OIDCFedIdentityProviderConfig(IdentityProviderModel model) {
        super(model);
    }
    
    public String getClientRegistrationTypes() {
        return getConfig().get(CLIENT_REGISTRATION_TYPES);
    }
    public void setClientRegistrationTypes(String clientRegistrationTypes) {
        getConfig().put(CLIENT_REGISTRATION_TYPES, clientRegistrationTypes);
    }
    
    public String getOrganizationName() {
        return getConfig().get(ORGANIZATION_NAME);
    }
    public void setOrganizationName(String organizationName) {
        getConfig().put(ORGANIZATION_NAME, organizationName);
    }
    
    public List<String> getAuthorityHints() {
        try {
            return JsonSerialization.readValue(getConfig().get(AUTHORITY_HINTS), List.class) ;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public void setAuthorityHints(List<String> authorityHints) {
        try {
            getConfig().put(AUTHORITY_HINTS, JsonSerialization.writeValueAsString(authorityHints) );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public Long getExpired() {
        return Long.valueOf(getConfig().get(EXPIRED));
    }
    public void setExpired(Long expired) {
        getConfig().put(EXPIRED, expired.toString());
    }
    
    public Set<String> getTrustAnchorIds() {
        try {
            return JsonSerialization.readValue(getConfig().get(TRUST_ANCHOR_IDS), Set.class) ;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public void setTrustAnchorIds(Set<String> trustAnchorIds) {
        try {
            getConfig().put(TRUST_ANCHOR_IDS, JsonSerialization.writeValueAsString(trustAnchorIds) );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public String getOpEntityIdentifier() {
        return getConfig().get(OP_ENTITY_IDENTIFIER);
    }
    public void setOpEntityIdentifier(String opEntityIdentifier) {
        getConfig().put(OP_ENTITY_IDENTIFIER, opEntityIdentifier);
    }

    @Override
    public void validate(RealmModel realm) {
        super.validate(realm);
        if (getAuthorityHints() == null || getAuthorityHints().isEmpty())
            throw new IllegalArgumentException("Authority Hints are required");
        if (getTrustAnchorIds() == null || getTrustAnchorIds().isEmpty())
            throw new IllegalArgumentException("Trust anchors ids are required");
        getAuthorityHints().stream().forEach(auth -> checkUrl(SslRequired.NONE, auth, "Authority hints"));
        getTrustAnchorIds().stream().forEach(auth -> checkUrl(SslRequired.NONE, auth, "Trust anchors ids"));
        checkUrl(SslRequired.NONE, getOpEntityIdentifier(), "OP entity Identifier");

        
    }

}
