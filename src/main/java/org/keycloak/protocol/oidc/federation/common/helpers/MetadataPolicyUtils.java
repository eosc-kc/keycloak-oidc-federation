package org.keycloak.protocol.oidc.federation.common.helpers;

import org.keycloak.protocol.oidc.federation.common.beans.EntityStatement;
import org.keycloak.protocol.oidc.federation.common.beans.MetadataPolicy;
import org.keycloak.protocol.oidc.federation.common.beans.RPMetadata;
import org.keycloak.protocol.oidc.federation.common.beans.RPMetadataPolicy;
import org.keycloak.protocol.oidc.federation.common.exceptions.MetadataPolicyCombinationException;
import org.keycloak.protocol.oidc.federation.common.exceptions.MetadataPolicyException;

public class MetadataPolicyUtils {

    public static RPMetadataPolicy combineClientPOlicies(
        RPMetadataPolicy superior, RPMetadataPolicy inferior)
        throws MetadataPolicyCombinationException {
        if (inferior == null)
            return superior;
        if (superior == null)
            return inferior;

        if (superior.getApplication_type() != null) {
            superior.setApplication_type(superior.getApplication_type().combinePolicy(inferior.getApplication_type()));
        } else {
            superior.setApplication_type(inferior.getApplication_type());
        }

        if (superior.getClient_id_issued_at() != null) {
            superior.setClient_id_issued_at(superior.getClient_id_issued_at().combinePolicy(inferior.getClient_id_issued_at()));
        } else {
            superior.setClient_id_issued_at(inferior.getClient_id_issued_at());
        }

        if (superior.getClient_name() != null) {
            superior.setClient_name(superior.getClient_name().combinePolicy(inferior.getClient_name()));
        } else {
            superior.setClient_name(inferior.getClient_name());
        }

        if (superior.getClient_registration_types() != null) {
            superior.setClient_registration_types(
                superior.getClient_registration_types().combinePolicy(inferior.getClient_registration_types()));
        } else {
            superior.setClient_registration_types(inferior.getClient_registration_types());
        }

        if (superior.getClient_secret_expires_at() != null) {
            superior.setClient_secret_expires_at(
                superior.getClient_secret_expires_at().combinePolicy(inferior.getClient_secret_expires_at()));
        } else {
            superior.setClient_secret_expires_at(inferior.getClient_secret_expires_at());
        }

        if (superior.getClient_uri() != null) {
            superior.setClient_uri(superior.getClient_uri().combinePolicy(inferior.getClient_uri()));
        } else {
            superior.setClient_uri(inferior.getClient_uri());
        }

        if (superior.getContacts() != null) {
            superior.setContacts(superior.getContacts().combinePolicy(inferior.getContacts()));
        } else {
            superior.setContacts(inferior.getContacts());
        }

        if (superior.getDefault_acr_values() != null) {
            superior.setDefault_acr_values(superior.getDefault_acr_values().combinePolicy(inferior.getDefault_acr_values()));
        } else {
            superior.setDefault_acr_values(inferior.getDefault_acr_values());
        }

        if (superior.getDefault_max_age() != null) {
            superior.setDefault_max_age(superior.getDefault_max_age().combinePolicy(inferior.getDefault_max_age()));
        } else {
            superior.setDefault_max_age(inferior.getDefault_max_age());
        }

        if (superior.getGrant_types() != null) {
            superior.setGrant_types(superior.getGrant_types().combinePolicy(inferior.getGrant_types()));
        } else {
            superior.setGrant_types(inferior.getGrant_types());
        }

        if (superior.getId_token_encrypted_response_alg() != null) {
            superior.setId_token_encrypted_response_alg(
                superior.getId_token_encrypted_response_alg().combinePolicy(inferior.getId_token_encrypted_response_alg()));
        } else {
            superior.setId_token_encrypted_response_alg(inferior.getId_token_encrypted_response_alg());
        }

        if (superior.getId_token_encrypted_response_enc() != null) {
            superior.setId_token_encrypted_response_enc(
                superior.getId_token_encrypted_response_enc().combinePolicy(inferior.getId_token_encrypted_response_enc()));
        } else {
            superior.setId_token_encrypted_response_enc(inferior.getId_token_encrypted_response_enc());
        }

        if (superior.getId_token_signed_response_alg() != null) {
            superior.setId_token_signed_response_alg(
                superior.getId_token_signed_response_alg().combinePolicy(inferior.getId_token_signed_response_alg()));
        } else {
            superior.setId_token_signed_response_alg(inferior.getId_token_signed_response_alg());
        }

        if (superior.getInitiate_login_uri() != null) {
            superior.setInitiate_login_uri(superior.getInitiate_login_uri().combinePolicy(inferior.getInitiate_login_uri()));
        } else {
            superior.setInitiate_login_uri(inferior.getInitiate_login_uri());
        }

        if (superior.getJwks_uri() != null) {
            superior.setJwks_uri(superior.getJwks_uri().combinePolicy(inferior.getJwks_uri()));
        } else {
            superior.setJwks_uri(inferior.getJwks_uri());
        }

        if (superior.getLogo_uri() != null) {
            superior.setLogo_uri(superior.getLogo_uri().combinePolicy(inferior.getLogo_uri()));
        } else {
            superior.setLogo_uri(inferior.getLogo_uri());
        }

        if (superior.getOrganization_name() != null) {
            superior.setOrganization_name(superior.getOrganization_name().combinePolicy(inferior.getOrganization_name()));
        } else {
            superior.setOrganization_name(inferior.getOrganization_name());
        }

        if (superior.getPolicy_uri() != null) {
            superior.setPolicy_uri(superior.getPolicy_uri().combinePolicy(inferior.getPolicy_uri()));
        } else {
            superior.setPolicy_uri(inferior.getPolicy_uri());
        }

        if (superior.getPost_logout_redirect_uris() != null) {
            superior.setPost_logout_redirect_uris(
                superior.getPost_logout_redirect_uris().combinePolicy(inferior.getPost_logout_redirect_uris()));
        } else {
            superior.setPost_logout_redirect_uris(inferior.getPost_logout_redirect_uris());
        }

        if (superior.getRedirect_uris() != null) {
            superior.setRedirect_uris(superior.getRedirect_uris().combinePolicy(inferior.getRedirect_uris()));
        } else {
            superior.setRedirect_uris(inferior.getRedirect_uris());
        }

        if (superior.getRegistration_access_token() != null) {
            superior.setRegistration_access_token(
                superior.getRegistration_access_token().combinePolicy(inferior.getRegistration_access_token()));
        } else {
            superior.setRegistration_access_token(inferior.getRegistration_access_token());
        }

        if (superior.getRegistration_client_uri() != null) {
            superior.setRegistration_client_uri(
                superior.getRegistration_client_uri().combinePolicy(inferior.getRegistration_client_uri()));
        } else {
            superior.setRegistration_client_uri(inferior.getRegistration_client_uri());
        }

        if (superior.getRequest_object_encryption_alg() != null) {
            superior.setRequest_object_encryption_alg(
                superior.getRequest_object_encryption_alg().combinePolicy(inferior.getRequest_object_encryption_alg()));
        } else {
            superior.setRequest_object_encryption_alg(inferior.getRequest_object_encryption_alg());
        }

        if (superior.getRequest_object_encryption_enc() != null) {
            superior.setRequest_object_encryption_enc(
                superior.getRequest_object_encryption_enc().combinePolicy(inferior.getRequest_object_encryption_enc()));
        } else {
            superior.setRequest_object_encryption_enc(inferior.getRequest_object_encryption_enc());
        }

        if (superior.getRequest_object_signing_alg() != null) {
            superior.setRequest_object_signing_alg(
                superior.getRequest_object_signing_alg().combinePolicy(inferior.getRequest_object_signing_alg()));
        } else {
            superior.setRequest_object_signing_alg(inferior.getRequest_object_signing_alg());
        }

        if (superior.getRequest_uris() != null) {
            superior.setRequest_uris(superior.getRequest_uris().combinePolicy(inferior.getRequest_uris()));
        } else {
            superior.setRequest_uris(inferior.getRequest_uris());
        }

        if (superior.getRequire_auth_time() != null) {
            superior.setRequire_auth_time(superior.getRequire_auth_time().combinePolicy(inferior.getRequire_auth_time()));
        } else {
            superior.setRequire_auth_time(inferior.getRequire_auth_time());
        }

        if (superior.getResponse_types() != null) {
            superior.setResponse_types(superior.getResponse_types().combinePolicy(inferior.getResponse_types()));
        } else {
            superior.setResponse_types(inferior.getResponse_types());
        }

        if (superior.getScope() != null) {
            superior.setScope(superior.getScope().combinePolicy(inferior.getScope()));
        } else {
            superior.setScope(inferior.getScope());
        }

        if (superior.getSector_identifier_uri() != null) {
            superior.setSector_identifier_uri(
                superior.getSector_identifier_uri().combinePolicy(inferior.getSector_identifier_uri()));
        } else {
            superior.setSector_identifier_uri(inferior.getSector_identifier_uri());
        }

        if (superior.getSoftware_id() != null) {
            superior.setSoftware_id(superior.getSoftware_id().combinePolicy(inferior.getSoftware_id()));
        } else {
            superior.setSoftware_id(inferior.getSoftware_id());
        }

        if (superior.getSoftware_version() != null) {
            superior.setSoftware_version(superior.getSoftware_version().combinePolicy(inferior.getSoftware_version()));
        } else {
            superior.setSoftware_version(inferior.getSoftware_version());
        }

        if (superior.getSubject_type() != null) {
            superior.setSubject_type(superior.getSubject_type().combinePolicy(inferior.getSubject_type()));
        } else {
            superior.setSubject_type(inferior.getSubject_type());
        }

        if (superior.getTls_client_auth_subject_dn() != null) {
            superior.setTls_client_auth_subject_dn(
                superior.getTls_client_auth_subject_dn().combinePolicy(inferior.getTls_client_auth_subject_dn()));
        } else {
            superior.setTls_client_auth_subject_dn(inferior.getTls_client_auth_subject_dn());
        }

        if (superior.getTls_client_certificate_bound_access_tokens() != null) {
            superior.setTls_client_certificate_bound_access_tokens(superior.getTls_client_certificate_bound_access_tokens()
                .combinePolicy(inferior.getTls_client_certificate_bound_access_tokens()));
        } else {
            superior.setTls_client_certificate_bound_access_tokens(inferior.getTls_client_certificate_bound_access_tokens());
        }

        if (superior.getToken_endpoint_auth_method() != null) {
            superior.setToken_endpoint_auth_method(
                superior.getToken_endpoint_auth_method().combinePolicy(inferior.getToken_endpoint_auth_method()));
        } else {
            superior.setToken_endpoint_auth_method(inferior.getToken_endpoint_auth_method());
        }

        if (superior.getToken_endpoint_auth_signing_alg() != null) {
            superior.setToken_endpoint_auth_signing_alg(
                superior.getToken_endpoint_auth_signing_alg().combinePolicy(inferior.getToken_endpoint_auth_signing_alg()));
        } else {
            superior.setToken_endpoint_auth_signing_alg(inferior.getToken_endpoint_auth_signing_alg());
        }

        if (superior.getTos_uri() != null) {
            superior.setTos_uri(superior.getTos_uri().combinePolicy(inferior.getTos_uri()));
        } else {
            superior.setTos_uri(inferior.getTos_uri());
        }

        if (superior.getUserinfo_encrypted_response_alg() != null) {
            superior.setUserinfo_encrypted_response_alg(
                superior.getUserinfo_encrypted_response_alg().combinePolicy(inferior.getUserinfo_encrypted_response_alg()));
        } else {
            superior.setUserinfo_encrypted_response_alg(inferior.getUserinfo_encrypted_response_alg());
        }

        if (superior.getUserinfo_encrypted_response_enc() != null) {
            superior.setUserinfo_encrypted_response_enc(
                superior.getUserinfo_encrypted_response_enc().combinePolicy(inferior.getUserinfo_encrypted_response_enc()));
        } else {
            superior.setUserinfo_encrypted_response_enc(inferior.getUserinfo_encrypted_response_enc());
        }

        if (superior.getUserinfo_signed_response_alg() != null) {
            superior.setUserinfo_signed_response_alg(
                superior.getUserinfo_signed_response_alg().combinePolicy(inferior.getUserinfo_signed_response_alg()));
        } else {
            superior.setUserinfo_signed_response_alg(inferior.getUserinfo_signed_response_alg());
        }

        return superior;
    }

    public static EntityStatement applyPoliciesToRPStatement(EntityStatement entity ,RPMetadataPolicy policy ) throws MetadataPolicyException,MetadataPolicyCombinationException {
        
        if (entity.getMetadata().getRp() == null)
            throw new MetadataPolicyException("Try to enforce metapolicy for RP to an entity statement withoup RP");
        
        if (policy == null)
            return entity;
        
        RPMetadata rp = entity.getMetadata().getRp();
        
        if (policy.getApplication_type() != null) {
            rp.setApplicationType(policy.getApplication_type().enforcePolicy(rp.getApplicationType(),"ApplicationType"));
        } 
        
        if (policy.getClient_id_issued_at() != null) {
            rp.setClientIdIssuedAt(policy.getClient_id_issued_at().enforcePolicy(rp.getClientIdIssuedAt(),"ClientIdIssuedAt"));
        } 
        
        if (policy.getClient_name() != null) {
            rp.setClientName(policy.getClient_name().enforcePolicy(rp.getClientName(),"ClientName"));
        } 
        
        if (policy.getClient_registration_types() != null) {
            rp.setClient_registration_types(policy.getClient_registration_types().enforcePolicy(rp.getClient_registration_types(),"Client_registration_types"));
        } 
        
        if (policy.getClient_secret_expires_at() != null) {
            rp.setClientSecretExpiresAt(policy.getClient_secret_expires_at().enforcePolicy(rp.getClientSecretExpiresAt(),"ClientSecretExpiresAt"));
        }
        
        if (policy.getClient_uri() != null) {
            rp.setClientUri(policy.getClient_uri().enforcePolicy(rp.getClientUri(),"ClientUri"));
        } 
        
        if (policy.getContacts() != null) {
            rp.setContacts(policy.getContacts().enforcePolicy(rp.getContacts(),"Contacts"));
        } 
        
        if (policy.getDefault_acr_values() != null) {
            rp.setDefaultAcrValues(policy.getDefault_acr_values().enforcePolicy(rp.getDefaultAcrValues(),"DefaultAcrValues"));
        } 
        
        if (policy.getDefault_max_age() != null) {
            rp.setDefaultMaxAge(policy.getDefault_max_age().enforcePolicy(rp.getDefaultMaxAge(),"DefaultMaxAge"));
        } 
        
        if (policy.getGrant_types() != null) {
             rp.setGrantTypes(policy.getGrant_types().enforcePolicy(rp.getGrantTypes(),"GrantTypes"));
        } 
        
        if (policy.getId_token_encrypted_response_alg() != null) {
            rp.setIdTokenEncryptedResponseAlg(policy.getId_token_encrypted_response_alg().enforcePolicy(rp.getIdTokenEncryptedResponseAlg(),"IdTokenEncryptedResponseAlg"));
        }
        
        if (policy.getId_token_encrypted_response_enc() != null) {
            rp.setIdTokenEncryptedResponseEnc(policy.getId_token_encrypted_response_enc().enforcePolicy(rp.getIdTokenEncryptedResponseEnc(),"IdTokenEncryptedResponseEnc"));
        } 
        
        if (policy.getId_token_signed_response_alg() != null) {
            rp.setIdTokenSignedResponseAlg(policy.getId_token_signed_response_alg().enforcePolicy(rp.getIdTokenSignedResponseAlg(),"IdTokenSignedResponseAlg"));
        } 
        
        if (policy.getInitiate_login_uri() != null) {
            rp.setInitiateLoginUri(policy.getInitiate_login_uri().enforcePolicy(rp.getInitiateLoginUri(),"InitiateLoginUri"));
        } 
        
        if (policy.getJwks_uri() != null) {
            rp.setJwksUri(policy.getJwks_uri().enforcePolicy(rp.getJwksUri(),"JwksUri"));
        } 
        
        if (policy.getLogo_uri() != null) {
            rp.setLogoUri(policy.getLogo_uri().enforcePolicy(rp.getLogoUri(),"LogoUri"));
        } 
        
        if (policy.getOrganization_name() != null) {
            rp.setOrganization_name(policy.getOrganization_name().enforcePolicy(rp.getOrganization_name(),"Organization_name"));
        } 
        
        if (policy.getPolicy_uri() != null) {
            rp.setPolicyUri(policy.getPolicy_uri().enforcePolicy(rp.getPolicyUri(),"PolicyUri"));
        } 
        
        if (policy.getPost_logout_redirect_uris() != null) {
            rp.setPostLogoutRedirectUris(policy.getPost_logout_redirect_uris().enforcePolicy(rp.getPostLogoutRedirectUris(),"PostLogoutRedirectUris"));
        } 
        
        if (policy.getRedirect_uris() != null) {
            rp.setRedirectUris(policy.getRedirect_uris().enforcePolicy(rp.getRedirectUris(),"RedirectUris"));
        } 
        
        if (policy.getRegistration_access_token() != null) {
            rp.setRegistrationAccessToken(policy.getRegistration_access_token().enforcePolicy(rp.getRegistrationAccessToken(),"RegistrationAccessToken"));
        } 
        
        if (policy.getRegistration_client_uri() != null) {
            rp.setRegistrationClientUri(policy.getRegistration_client_uri().enforcePolicy(rp.getRegistrationClientUri(),"RegistrationClientUri"));
        } 
        
        if (policy.getRequest_object_encryption_alg() != null) {
            rp.setRequestObjectEncryptionAlg(policy.getRequest_object_encryption_alg().enforcePolicy(rp.getRequestObjectEncryptionAlg(),"RequestObjectEncryptionAlg"));
        } 
        
        if (policy.getRequest_object_encryption_enc() != null) {
            rp.setRequestObjectEncryptionEnc(policy.getRequest_object_encryption_enc().enforcePolicy(rp.getRequestObjectEncryptionEnc(),"RequestObjectEncryptionEnc"));
        } 
        
        if (policy.getRequest_object_signing_alg() != null) {
            rp.setRequestObjectSigningAlg(policy.getRequest_object_signing_alg().enforcePolicy(rp.getRequestObjectSigningAlg(),"RequestObjectSigningAlg"));
        } 
        
        if (policy.getRequest_uris() != null) {
            rp.setRequestUris(policy.getRequest_uris().enforcePolicy(rp.getRequestUris(),"RequestUris"));
        } 
        
        if (policy.getRequire_auth_time() != null) {
            rp.setRequireAuthTime(policy.getRequire_auth_time().enforcePolicy(rp.getRequireAuthTime(),"RequireAuthTime"));
        } 
        
        if (policy.getResponse_types() != null) {
            rp.setResponseTypes(policy.getResponse_types().enforcePolicy(rp.getResponseTypes(),"ResponseTypes"));
        } 
        
        if (policy.getScope() != null) {
            rp.setScope(policy.getScope().enforcePolicy(rp.getScope(),"Scope"));
        } 
        
        if (policy.getSector_identifier_uri() != null) {
            rp.setSectorIdentifierUri(policy.getSector_identifier_uri().enforcePolicy(rp.getSectorIdentifierUri(),"SectorIdentifierUri"));
        } 
        
        if (policy.getSoftware_id() != null) {
            rp.setSoftwareId(policy.getSoftware_id().enforcePolicy(rp.getSoftwareId(),"SoftwareId"));
        } 
        
        if (policy.getSoftware_version() != null) {
            rp.setSoftwareVersion(policy.getSoftware_version().enforcePolicy(rp.getSoftwareVersion(),"SoftwareVersion"));
        } 
        
        if (policy.getSubject_type() != null) {
            rp.setSubjectType(policy.getSubject_type().enforcePolicy(rp.getSubjectType(),"SubjectType"));
        }
        
        if (policy.getTls_client_auth_subject_dn() != null) {
            rp.setTlsClientAuthSubjectDn(policy.getTls_client_auth_subject_dn().enforcePolicy(rp.getTlsClientAuthSubjectDn(),"TlsClientAuthSubjectDn"));
        } 
        
        if (policy.getTls_client_certificate_bound_access_tokens() != null) {
            rp.setTlsClientCertificateBoundAccessTokens(policy.getTls_client_certificate_bound_access_tokens().enforcePolicy(rp.getTlsClientCertificateBoundAccessTokens(),"TlsClientCertificateBoundAccessTokens"));
        }
        
        if (policy.getToken_endpoint_auth_method() != null) {
            rp.setTokenEndpointAuthMethod(policy.getToken_endpoint_auth_method().enforcePolicy(rp.getTokenEndpointAuthMethod(),"TokenEndpointAuthMethod"));
        }
        
        if (policy.getToken_endpoint_auth_signing_alg() != null) {
            rp.setTokenEndpointAuthSigningAlg(policy.getToken_endpoint_auth_signing_alg().enforcePolicy(rp.getTokenEndpointAuthSigningAlg(),"TokenEndpointAuthSigningAlg"));
        }
        
        if (policy.getTos_uri() != null) {
            rp.setTosUri(policy.getTos_uri().enforcePolicy(rp.getTosUri(),"TosUri"));
        } 
        
        if (policy.getUserinfo_encrypted_response_alg() != null) {
            rp.setUserinfoEncryptedResponseAlg(policy.getUserinfo_encrypted_response_alg().enforcePolicy(rp.getUserinfoEncryptedResponseAlg(),"UserinfoEncryptedResponseAlg"));
        }
        
        if (policy.getUserinfo_encrypted_response_enc() != null) {
            rp.setUserinfoEncryptedResponseEnc(policy.getUserinfo_encrypted_response_enc().enforcePolicy(rp.getUserinfoEncryptedResponseEnc(),"UserinfoEncryptedResponseEnc"));
        } 
        
        if (policy.getUserinfo_signed_response_alg() != null) {
            rp.setUserinfoSignedResponseAlg(policy.getUserinfo_signed_response_alg().enforcePolicy(rp.getUserinfoSignedResponseAlg(),"UserinfoSignedResponseAlg"));
        } 
        
        MetadataPolicy metadataPolicy = new MetadataPolicy();
        metadataPolicy.setRpPolicy(policy);
        entity.setMetadataPolicy(metadataPolicy);
        return entity;
    }

}
