package org.keycloak.protocol.oidc.federation.op.model;

import java.util.Collections;
import java.util.List;

import org.keycloak.connections.jpa.entityprovider.JpaEntityProvider;

public class OIDCFedConfigJpaEntityProvider  implements JpaEntityProvider {

    // List of your JPA entities.
    @Override
    public List<Class<?>> getEntities() {
        return Collections.<Class<?>>singletonList(OIDCFedConfigEntity.class);
    }

    // This is used to return the location of the Liquibase changelog file.
    // You can return null if you don't want Liquibase to create and update the DB schema.
    @Override
    public String getChangelogLocation() {
        return "META-INF/oidc-federation-changelog.xml";
    }

    // Helper method, which will be used internally by Liquibase.
    @Override
    public String getFactoryId() {
        return OIDCFedConfigJpaEntityProviderFactory.ID;
    }

    @Override
    public void close() {
        
    }

}