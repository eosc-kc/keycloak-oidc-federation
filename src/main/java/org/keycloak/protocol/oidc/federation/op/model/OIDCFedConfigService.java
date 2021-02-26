package org.keycloak.protocol.oidc.federation.op.model;

import javax.persistence.EntityManager;
import javax.ws.rs.NotFoundException;

import org.keycloak.connections.jpa.JpaConnectionProvider;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

public class OIDCFedConfigService {
    
    private final KeycloakSession session;

    private final EntityManager em;

    public OIDCFedConfigService(KeycloakSession session) {
        this.session = session;
        if (getRealm() == null) {
            throw new IllegalStateException("The service cannot accept a session without a realm in its context.");
        }
        em = session.getProvider(JpaConnectionProvider.class).getEntityManager();
    }

    private RealmModel getRealm() {
        return session.getContext().getRealm();
    }

    public OIDCFedConfigEntity getEntity() {
        return em.find(OIDCFedConfigEntity.class, getRealm().getId());
    }
    
    public void saveEntity(OIDCFedConfigEntity config) {
        em.persist(config);
    }
    
    
    public void deleteEntity() throws NotFoundException{
        OIDCFedConfigEntity entity = em.find(OIDCFedConfigEntity.class, getRealm().getId());
        if (entity == null) 
            throw new NotFoundException(String.format("Realm %s does not have", getRealm().getName()));
        em.remove(entity);
    }

}
