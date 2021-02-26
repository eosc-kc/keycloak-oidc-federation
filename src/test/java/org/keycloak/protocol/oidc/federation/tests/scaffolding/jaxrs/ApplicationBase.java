package org.keycloak.protocol.oidc.federation.tests.scaffolding.jaxrs;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

import org.keycloak.protocol.oidc.federation.tests.scaffolding.intermediates.EntityStatementsREST;

public class ApplicationBase extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new LinkedHashSet<Class<?>>();
        resources.add(EntityStatementsREST.class);
        return resources;
    }

}