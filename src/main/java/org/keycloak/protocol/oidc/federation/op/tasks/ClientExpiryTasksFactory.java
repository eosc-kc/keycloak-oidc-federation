package org.keycloak.protocol.oidc.federation.op.tasks;

import org.keycloak.Config.Scope;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.protocol.oidc.federation.common.helpers.FedUtils;

public class ClientExpiryTasksFactory implements ClientExpiryTasksFactoryI {

    public static final String PROVIDER_ID = "client-expiry-tasks";

    @Override
    public ClientExpiryTasks create(KeycloakSession session) {
        return new ClientExpiryTasks(session);
    }

    @Override
    public void init(Scope config) {
    }

    @Override
    public void postInit(KeycloakSessionFactory sessionFactory) {
        KeycloakSession session = sessionFactory.create();
        session.getTransactionManager().begin();
        ClientExpiryTasks cl= this.create(session);
        session.realms().getRealms().stream().forEach(realm -> realm.getClients().stream().forEach(client -> {
            if (client.getAttributes().containsKey(FedUtils.SECRET_EXPIRES_AT)) {
                cl.scheduleTask(client.getId(), realm.getId(), Long.valueOf(client.getAttributes().get(FedUtils.SECRET_EXPIRES_AT)));
            }
        }));
        session.getTransactionManager().commit();
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }


}
