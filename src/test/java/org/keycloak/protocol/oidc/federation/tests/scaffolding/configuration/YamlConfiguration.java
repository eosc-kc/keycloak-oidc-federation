package org.keycloak.protocol.oidc.federation.tests.scaffolding.configuration;

import java.io.IOException;
import java.io.InputStream;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlConfiguration {

    private static final String CONFIG_FILENAME = "application.yml";
    
    static Configuration config = new Configuration();
    
    static {
        ObjectMapper om = new ObjectMapper(new YAMLFactory());
        InputStream is = YamlConfiguration.class.getClassLoader().getResourceAsStream(CONFIG_FILENAME);
        try {
            config = om.readValue(is, Configuration.class);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Could not load testing configuration from file %s. Could not load test suite.", CONFIG_FILENAME));
        }
    }
    
    public static Configuration getConfig() {
        return config;
    }
    
}
