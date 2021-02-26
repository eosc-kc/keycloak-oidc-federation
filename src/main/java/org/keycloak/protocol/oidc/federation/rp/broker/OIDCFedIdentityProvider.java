package org.keycloak.protocol.oidc.federation.rp.broker;

import org.keycloak.broker.oidc.OIDCIdentityProvider;
import org.keycloak.broker.oidc.OIDCIdentityProviderConfig;
import org.keycloak.models.KeycloakSession;

public class OIDCFedIdentityProvider extends OIDCIdentityProvider {

    public OIDCFedIdentityProvider(KeycloakSession session, OIDCFedIdentityProviderConfig config) {
        super(session, config);
    }

}
