package org.keycloak.protocol.oidc.federation.common.beans.constraints;

public class Constraints {

    private NamingConstraints naming_constraints;
    private Integer max_path_length;
    
    
    public NamingConstraints getNaming_constraints() {
        return naming_constraints;
    }
    public void setNaming_constraints(NamingConstraints naming_constraints) {
        this.naming_constraints = naming_constraints;
    }
    public Integer getMax_path_length() {
        return max_path_length;
    }
    public void setMax_path_length(Integer max_path_length) {
        this.max_path_length = max_path_length;
    }
    
    
}
