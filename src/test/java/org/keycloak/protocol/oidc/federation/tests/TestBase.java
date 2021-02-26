package org.keycloak.protocol.oidc.federation.tests;

import java.net.MalformedURLException;
import java.net.URL;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;
import org.junit.After;
import org.junit.Before;
import org.keycloak.protocol.oidc.federation.tests.scaffolding.configuration.YamlConfiguration;
import org.keycloak.protocol.oidc.federation.tests.scaffolding.jaxrs.ApplicationBase;

import io.undertow.Undertow;
import io.undertow.servlet.api.DeploymentInfo;

public abstract class TestBase {

    private UndertowJaxrsServer server;
    
    
    @Before
    public void init() throws MalformedURLException {
        
        URL url = new URL(YamlConfiguration.getConfig().getHost());
        
        server = new UndertowJaxrsServer();
        Undertow.Builder serverBuilder = Undertow.builder().addHttpListener(url.getPort(), url.getHost());
        server.start(serverBuilder);
        
        
        ResteasyDeployment deployment = new ResteasyDeployment();
        deployment.setApplicationClass(ApplicationBase.class.getName());
        DeploymentInfo di = server.undertowDeployment(deployment, "/");
        
        di.setClassLoader(TestBase.class.getClassLoader())
                .setContextPath("/")
                .setDeploymentName("Test Application")
//                .addServlets(Servlets.servlet("helloServlet", HelloServlet.class).addMapping("/hello"))
                ;
        
        
        server.deploy(di);
    }
    
    
    @After
    public void shutdown() throws MalformedURLException {
        server.stop();
    }
    
    
}
