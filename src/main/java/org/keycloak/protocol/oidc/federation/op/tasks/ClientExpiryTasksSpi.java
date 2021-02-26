package org.keycloak.protocol.oidc.federation.op.tasks;

import org.keycloak.provider.Provider;
import org.keycloak.provider.ProviderFactory;
import org.keycloak.provider.Spi;

public class ClientExpiryTasksSpi implements Spi{

    @Override
    public boolean isInternal() {
        return true;
    }

    @Override
    public String getName() {
        return "client-expiry-tasks-spi";
    }

    
    @Override
    public Class<? extends Provider> getProviderClass() {
        return ClientExpiryTasksI.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return ClientExpiryTasksFactoryI.class;
    }
    

}
