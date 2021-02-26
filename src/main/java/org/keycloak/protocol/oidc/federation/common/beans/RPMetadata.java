package org.keycloak.protocol.oidc.federation.common.beans;

import java.util.List;

import org.keycloak.common.util.Time;
import org.keycloak.representations.oidc.OIDCClientRepresentation;

public class RPMetadata extends OIDCClientRepresentation {
    
    private List<String> client_registration_types;

    private String organization_name;

    private String trust_anchor_id;
    
    public RPMetadata() {
        
    }
    
    public RPMetadata(OIDCClientRepresentation oidcClient,List<String> client_registration_types,String organization_name) {
        this.setContacts(oidcClient.getContacts()); 
        this.setDefaultAcrValues(oidcClient.getDefaultAcrValues()); 
        this.setGrantTypes(oidcClient.getGrantTypes()); 
        this.setPostLogoutRedirectUris(oidcClient.getPostLogoutRedirectUris()); 
        this.setRedirectUris(oidcClient.getRedirectUris()); 
        this.setRequestUris(oidcClient.getRequestUris()); 
        this.setResponseTypes(oidcClient.getResponseTypes()); 
        this.setApplicationType(oidcClient.getApplicationType()); 
        this.setClientId(oidcClient.getClientId()); 
        this.setClientName(oidcClient.getClientName()); 
        this.setClientSecret(oidcClient.getClientSecret()); 
        this.setClientSecretExpiresAt(oidcClient.getClientSecretExpiresAt()); 
        this.setClientUri(oidcClient.getClientUri()); 
        this.setDefaultAcrValues(oidcClient.getDefaultAcrValues()); 
        this.setDefaultMaxAge(oidcClient.getDefaultMaxAge()); 
        this.setIdTokenEncryptedResponseAlg(oidcClient.getIdTokenEncryptedResponseAlg()); 
        this.setIdTokenEncryptedResponseEnc(oidcClient.getIdTokenEncryptedResponseEnc()); 
        this.setIdTokenSignedResponseAlg(oidcClient.getIdTokenSignedResponseAlg()); 
        this.setIdTokenSignedResponseAlg(oidcClient.getIdTokenSignedResponseAlg()); 
        this.setJwks(oidcClient.getJwks()); 
        this.setJwksUri(oidcClient.getJwksUri()); 
        this.setLogoUri(oidcClient.getLogoUri());
        this.setPolicyUri(oidcClient.getPolicyUri()); 
        this.setRegistrationAccessToken(oidcClient.getRegistrationAccessToken()); 
        this.setRegistrationClientUri(oidcClient.getRegistrationClientUri()); 
        this.setRequestObjectEncryptionAlg(oidcClient.getRequestObjectEncryptionAlg()); 
        this.setRequestObjectEncryptionEnc(oidcClient.getRequestObjectEncryptionEnc());         
        this.setRequestObjectSigningAlg(oidcClient.getRequestObjectSigningAlg()); 
        this.setRequireAuthTime(oidcClient.getRequireAuthTime()); 
        this.setScope(oidcClient.getScope());
        this.setSectorIdentifierUri(oidcClient.getSectorIdentifierUri()); 
        this.setSoftwareId(oidcClient.getSoftwareId()); 
        this.setSubjectType(oidcClient.getSubjectType()); 
        this.setTlsClientAuthSubjectDn(oidcClient.getTlsClientAuthSubjectDn()); 
        this.setTlsClientCertificateBoundAccessTokens(oidcClient.getTlsClientCertificateBoundAccessTokens()); 
        this.setTokenEndpointAuthMethod(oidcClient.getTokenEndpointAuthMethod()); 
        this.setTokenEndpointAuthSigningAlg(oidcClient.getTokenEndpointAuthSigningAlg()); 
        this.setTosUri(oidcClient.getTosUri());         
        this.setUserinfoEncryptedResponseAlg(oidcClient.getUserinfoEncryptedResponseAlg()); 
        this.setUserinfoEncryptedResponseEnc(oidcClient.getUserinfoEncryptedResponseEnc()); 
        this.setUserinfoSignedResponseAlg(oidcClient.getUserinfoSignedResponseAlg());
        this.client_registration_types =client_registration_types;
        this.organization_name = organization_name;
        this.setClientIdIssuedAt(Time.currentTime());
            
    }
    
    public List<String> getClient_registration_types() {
        return client_registration_types;
    }

    public void setClient_registration_types(List<String> client_registration_types) {
        this.client_registration_types = client_registration_types;
    }

    public String getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(String organization_name) {
        this.organization_name = organization_name;
    }

    public String getTrust_anchor_id() {
        return trust_anchor_id;
    }

    public void setTrust_anchor_id(String trust_anchor_id) {
        this.trust_anchor_id = trust_anchor_id;
    }
    
}
