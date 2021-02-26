package org.keycloak.protocol.oidc.federation.common.beans;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FederationEntity {
    
    @JsonProperty("federation_api_endpoint")
    private String federationApiEndpoint;
    private String name;
    private List<String> contacts;
    @JsonProperty("policy_uri")
    private String policyUri;
    @JsonProperty("homepage_uri")
    private String homepageUri;
    @JsonProperty("trust_marks")
    private String trustMarks;
    public String getFederationApiEndpoint() {
        return federationApiEndpoint;
    }
    public void setFederationApiEndpoint(String federationApiEndpoint) {
        this.federationApiEndpoint = federationApiEndpoint;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<String> getContacts() {
        return contacts;
    }
    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }
    public String getPolicyUri() {
        return policyUri;
    }
    public void setPolicyUri(String policyUri) {
        this.policyUri = policyUri;
    }
    public String getHomepageUri() {
        return homepageUri;
    }
    public void setHomepageUri(String homepageUri) {
        this.homepageUri = homepageUri;
    }
    public String getTrustMarks() {
        return trustMarks;
    }
    public void setTrustMarks(String trustMarks) {
        this.trustMarks = trustMarks;
    }
}
