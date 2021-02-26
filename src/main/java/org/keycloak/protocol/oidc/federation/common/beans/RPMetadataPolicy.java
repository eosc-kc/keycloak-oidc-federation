package org.keycloak.protocol.oidc.federation.common.beans;

public class RPMetadataPolicy {
    
    private PolicyList<String> client_registration_types;

    private Policy<String> organization_name;

    // OIDC Dynamic client registration properties

    private PolicyList<String> redirect_uris;

    private Policy<String> token_endpoint_auth_method;

    private Policy<String> token_endpoint_auth_signing_alg;

    private PolicyList<String> grant_types;

    private PolicyList<String> response_types;

    private Policy<String> application_type;

    private Policy<String> client_name;

    private Policy<String> client_uri;

    private Policy<String> logo_uri;

    private Policy<String> scope;

    private PolicyList<String> contacts;

    private Policy<String> tos_uri;

    private Policy<String> policy_uri;

    private Policy<String> jwks_uri;

    private Policy<String> sector_identifier_uri;

    private Policy<String> subject_type;

    private Policy<String> id_token_signed_response_alg;

    private Policy<String> id_token_encrypted_response_alg;

    private Policy<String> id_token_encrypted_response_enc;

    private Policy<String> userinfo_signed_response_alg;

    private Policy<String> userinfo_encrypted_response_alg;

    private Policy<String> userinfo_encrypted_response_enc;

    private Policy<String> request_object_signing_alg;

    private Policy<String> request_object_encryption_alg;

    private Policy<String> request_object_encryption_enc;

    private Policy<Integer> default_max_age;

    private Policy<Boolean> require_auth_time;

    private PolicyList<String> default_acr_values;

    private Policy<String> initiate_login_uri;

    private PolicyList<String> request_uris;

    // KEYCLOAK-6771 Certificate Bound Token
    // https://tools.ietf.org/html/draft-ietf-oauth-mtls-08#section-6.5
    private Policy<Boolean> tls_client_certificate_bound_access_tokens;

    private Policy<String> tls_client_auth_subject_dn;

    // OIDC Session Management
    private PolicyList<String> post_logout_redirect_uris;

    // Not sure from which specs this comes
    private Policy<String> software_id;

    private Policy<String> software_version;

    // OIDC Dynamic Client Registration Response
    private Policy<Integer> client_id_issued_at;

    private Policy<Integer> client_secret_expires_at;

    private Policy<String> registration_client_uri;

    private Policy<String> registration_access_token;
    
   

    public PolicyList<String> getClient_registration_types() {
        return client_registration_types;
    }

    public void setClient_registration_types(PolicyList<String> client_registration_types) {
        this.client_registration_types = client_registration_types;
    }

    public Policy<String> getOrganization_name() {
        return organization_name;
    }

    public void setOrganization_name(Policy<String> organization_name) {
        this.organization_name = organization_name;
    }

    public PolicyList<String> getRedirect_uris() {
        return redirect_uris;
    }

    public void setRedirect_uris(PolicyList<String> redirect_uris) {
        this.redirect_uris = redirect_uris;
    }

    public Policy<String> getToken_endpoint_auth_method() {
        return token_endpoint_auth_method;
    }

    public void setToken_endpoint_auth_method(Policy<String> token_endpoint_auth_method) {
        this.token_endpoint_auth_method = token_endpoint_auth_method;
    }

    public Policy<String> getToken_endpoint_auth_signing_alg() {
        return token_endpoint_auth_signing_alg;
    }

    public void setToken_endpoint_auth_signing_alg(Policy<String> token_endpoint_auth_signing_alg) {
        this.token_endpoint_auth_signing_alg = token_endpoint_auth_signing_alg;
    }

    public PolicyList<String> getGrant_types() {
        return grant_types;
    }

    public void setGrant_types(PolicyList<String> grant_types) {
        this.grant_types = grant_types;
    }

    public PolicyList<String> getResponse_types() {
        return response_types;
    }

    public void setResponse_types(PolicyList<String> response_types) {
        this.response_types = response_types;
    }

    public Policy<String> getApplication_type() {
        return application_type;
    }

    public void setApplication_type(Policy<String> application_type) {
        this.application_type = application_type;
    }

    public Policy<String> getClient_name() {
        return client_name;
    }

    public void setClient_name(Policy<String> client_name) {
        this.client_name = client_name;
    }

    public Policy<String> getClient_uri() {
        return client_uri;
    }

    public void setClient_uri(Policy<String> client_uri) {
        this.client_uri = client_uri;
    }

    public Policy<String> getLogo_uri() {
        return logo_uri;
    }

    public void setLogo_uri(Policy<String> logo_uri) {
        this.logo_uri = logo_uri;
    }

    public Policy<String> getScope() {
        return scope;
    }

    public void setScope(Policy<String> scope) {
        this.scope = scope;
    }

    public PolicyList<String> getContacts() {
        return contacts;
    }

    public void setContacts(PolicyList<String> contacts) {
        this.contacts = contacts;
    }

    public Policy<String> getTos_uri() {
        return tos_uri;
    }

    public void setTos_uri(Policy<String> tos_uri) {
        this.tos_uri = tos_uri;
    }

    public Policy<String> getPolicy_uri() {
        return policy_uri;
    }

    public void setPolicy_uri(Policy<String> policy_uri) {
        this.policy_uri = policy_uri;
    }

    public Policy<String> getJwks_uri() {
        return jwks_uri;
    }

    public void setJwks_uri(Policy<String> jwks_uri) {
        this.jwks_uri = jwks_uri;
    }

    public Policy<String> getSector_identifier_uri() {
        return sector_identifier_uri;
    }

    public void setSector_identifier_uri(Policy<String> sector_identifier_uri) {
        this.sector_identifier_uri = sector_identifier_uri;
    }

    public Policy<String> getSubject_type() {
        return subject_type;
    }

    public void setSubject_type(Policy<String> subject_type) {
        this.subject_type = subject_type;
    }

    public Policy<String> getId_token_signed_response_alg() {
        return id_token_signed_response_alg;
    }

    public void setId_token_signed_response_alg(Policy<String> id_token_signed_response_alg) {
        this.id_token_signed_response_alg = id_token_signed_response_alg;
    }

    public Policy<String> getId_token_encrypted_response_alg() {
        return id_token_encrypted_response_alg;
    }

    public void setId_token_encrypted_response_alg(Policy<String> id_token_encrypted_response_alg) {
        this.id_token_encrypted_response_alg = id_token_encrypted_response_alg;
    }

    public Policy<String> getId_token_encrypted_response_enc() {
        return id_token_encrypted_response_enc;
    }

    public void setId_token_encrypted_response_enc(Policy<String> id_token_encrypted_response_enc) {
        this.id_token_encrypted_response_enc = id_token_encrypted_response_enc;
    }

    public Policy<String> getUserinfo_signed_response_alg() {
        return userinfo_signed_response_alg;
    }

    public void setUserinfo_signed_response_alg(Policy<String> userinfo_signed_response_alg) {
        this.userinfo_signed_response_alg = userinfo_signed_response_alg;
    }

    public Policy<String> getUserinfo_encrypted_response_alg() {
        return userinfo_encrypted_response_alg;
    }

    public void setUserinfo_encrypted_response_alg(Policy<String> userinfo_encrypted_response_alg) {
        this.userinfo_encrypted_response_alg = userinfo_encrypted_response_alg;
    }

    public Policy<String> getUserinfo_encrypted_response_enc() {
        return userinfo_encrypted_response_enc;
    }

    public void setUserinfo_encrypted_response_enc(Policy<String> userinfo_encrypted_response_enc) {
        this.userinfo_encrypted_response_enc = userinfo_encrypted_response_enc;
    }

    public Policy<String> getRequest_object_signing_alg() {
        return request_object_signing_alg;
    }

    public void setRequest_object_signing_alg(Policy<String> request_object_signing_alg) {
        this.request_object_signing_alg = request_object_signing_alg;
    }

    public Policy<String> getRequest_object_encryption_alg() {
        return request_object_encryption_alg;
    }

    public void setRequest_object_encryption_alg(Policy<String> request_object_encryption_alg) {
        this.request_object_encryption_alg = request_object_encryption_alg;
    }

    public Policy<String> getRequest_object_encryption_enc() {
        return request_object_encryption_enc;
    }

    public void setRequest_object_encryption_enc(Policy<String> request_object_encryption_enc) {
        this.request_object_encryption_enc = request_object_encryption_enc;
    }

    public Policy<Integer> getDefault_max_age() {
        return default_max_age;
    }

    public void setDefault_max_age(Policy<Integer> default_max_age) {
        this.default_max_age = default_max_age;
    }

    public Policy<Boolean> getRequire_auth_time() {
        return require_auth_time;
    }

    public void setRequire_auth_time(Policy<Boolean> require_auth_time) {
        this.require_auth_time = require_auth_time;
    }

    public PolicyList<String> getDefault_acr_values() {
        return default_acr_values;
    }

    public void setDefault_acr_values(PolicyList<String> default_acr_values) {
        this.default_acr_values = default_acr_values;
    }

    public Policy<String> getInitiate_login_uri() {
        return initiate_login_uri;
    }

    public void setInitiate_login_uri(Policy<String> initiate_login_uri) {
        this.initiate_login_uri = initiate_login_uri;
    }

    public PolicyList<String> getRequest_uris() {
        return request_uris;
    }

    public void setRequest_uris(PolicyList<String> request_uris) {
        this.request_uris = request_uris;
    }

    public Policy<Boolean> getTls_client_certificate_bound_access_tokens() {
        return tls_client_certificate_bound_access_tokens;
    }

    public void setTls_client_certificate_bound_access_tokens(Policy<Boolean> tls_client_certificate_bound_access_tokens) {
        this.tls_client_certificate_bound_access_tokens = tls_client_certificate_bound_access_tokens;
    }

    public Policy<String> getTls_client_auth_subject_dn() {
        return tls_client_auth_subject_dn;
    }

    public void setTls_client_auth_subject_dn(Policy<String> tls_client_auth_subject_dn) {
        this.tls_client_auth_subject_dn = tls_client_auth_subject_dn;
    }

    public PolicyList<String> getPost_logout_redirect_uris() {
        return post_logout_redirect_uris;
    }

    public void setPost_logout_redirect_uris(PolicyList<String> post_logout_redirect_uris) {
        this.post_logout_redirect_uris = post_logout_redirect_uris;
    }

    public Policy<String> getSoftware_id() {
        return software_id;
    }

    public void setSoftware_id(Policy<String> software_id) {
        this.software_id = software_id;
    }

    public Policy<String> getSoftware_version() {
        return software_version;
    }

    public void setSoftware_version(Policy<String> software_version) {
        this.software_version = software_version;
    }

    public Policy<Integer> getClient_id_issued_at() {
        return client_id_issued_at;
    }

    public void setClient_id_issued_at(Policy<Integer> client_id_issued_at) {
        this.client_id_issued_at = client_id_issued_at;
    }

    public Policy<Integer> getClient_secret_expires_at() {
        return client_secret_expires_at;
    }

    public void setClient_secret_expires_at(Policy<Integer> client_secret_expires_at) {
        this.client_secret_expires_at = client_secret_expires_at;
    }

    public Policy<String> getRegistration_client_uri() {
        return registration_client_uri;
    }

    public void setRegistration_client_uri(Policy<String> registration_client_uri) {
        this.registration_client_uri = registration_client_uri;
    }

    public Policy<String> getRegistration_access_token() {
        return registration_access_token;
    }

    public void setRegistration_access_token(Policy<String> registration_access_token) {
        this.registration_access_token = registration_access_token;
    }

}
