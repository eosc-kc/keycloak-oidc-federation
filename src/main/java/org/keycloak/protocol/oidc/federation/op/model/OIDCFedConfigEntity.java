package org.keycloak.protocol.oidc.federation.op.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "OIDC_FEDERATION_CONFIG")
public class OIDCFedConfigEntity {

    @Id
    @Column(name = "REALM_ID", nullable = false)
    private String realmId;
    
    @Column(name = "CONFIGURATION", nullable = false)
    @Convert(converter = OIDCFedConfigJsonConverter.class)
    private OIDCFedConfig configuration;

    public OIDCFedConfigEntity() {
        
    }
    
    public OIDCFedConfigEntity(String realmId, OIDCFedConfig configuration) {
        this.realmId = realmId;
        this.configuration = configuration;
    }
    
    public String getRealmId() {
        return realmId;
    }

    public void setRealmId(String realmId) {
        this.realmId = realmId;
    }

    public OIDCFedConfig getConfiguration() {
        return configuration;
    }

    public void setConfiguration(OIDCFedConfig configuration) {
        this.configuration = configuration;
    }

    
}
