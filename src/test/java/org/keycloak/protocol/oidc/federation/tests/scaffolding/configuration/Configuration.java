package org.keycloak.protocol.oidc.federation.tests.scaffolding.configuration;

import java.util.List;
import java.util.Map;

import org.keycloak.protocol.oidc.federation.common.beans.RPMetadataPolicy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Configuration {
    
	private String host;
	private Map<String, IntermediateEntity> entities;

	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}	
	public Map<String, IntermediateEntity> getEntities() {
        return entities;
    }
    public void setEntities(Map<String, IntermediateEntity> entities) {
        this.entities = entities;
    }



    public static class IntermediateEntity {
	    private final ObjectMapper mapper = new ObjectMapper();
	    private List<String> authorityHints;
	    private RPMetadataPolicy metadataPolicies;
	    
        public List<String> getAuthorityHints() {
            return authorityHints;
        }
        public void setAuthorityHints(List<String> authorityHints) {
            this.authorityHints = authorityHints;
        }
        public RPMetadataPolicy getMetadataPolicies() {
            return metadataPolicies;
        }
        public void setMetadataPolicies(String metadataPolicies) {
            try {
                this.metadataPolicies = mapper.readValue(metadataPolicies, RPMetadataPolicy.class);
            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
	    	    
	}
    
	
}
