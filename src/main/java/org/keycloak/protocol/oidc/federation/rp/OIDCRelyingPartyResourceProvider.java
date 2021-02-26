package org.keycloak.protocol.oidc.federation.rp;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.keycloak.models.IdentityProviderModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.protocol.oidc.federation.rp.broker.OIDCFedIdentityProviderConfig;
import org.keycloak.protocol.oidc.federation.rp.broker.OIDCFedIdentityProviderFactory;
import org.keycloak.services.resource.RealmResourceProvider;

public class OIDCRelyingPartyResourceProvider implements RealmResourceProvider {
    
    private KeycloakSession session;

    public OIDCRelyingPartyResourceProvider(KeycloakSession session) {
        this.session = session;
    }
    
    @Override
    public void close() {
        
    }

    @Override
    public Object getResource() {
        return this;
    }
    
    
    @Path("{idpAlias}")
    public OIDCFedIdPResource getWellKnownService(@PathParam("idpAlias") String idpAlias) {
        RealmModel realm = session.getContext().getRealm();
        IdentityProviderModel idpModel = realm.getIdentityProviderByAlias(idpAlias);
        if (idpModel == null || !OIDCFedIdentityProviderFactory.PROVIDER_ID.equals(idpModel.getProviderId()))
            throw new NotFoundException("This OIDC Federation IdP not found");
        OIDCFedIdPResource rpWellKnown = new OIDCFedIdPResource(session, realm, new OIDCFedIdentityProviderConfig(idpModel));
        ResteasyProviderFactory.getInstance().injectProperties(rpWellKnown);
        return rpWellKnown;
    }
    
    

}
