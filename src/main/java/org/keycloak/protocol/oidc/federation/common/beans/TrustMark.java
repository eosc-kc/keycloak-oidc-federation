package org.keycloak.protocol.oidc.federation.common.beans;

public class TrustMark {

    private String id;
    private String iss;
    private String sub;
    private Long iat;
    private String mark;
    private Long exp;
    private String ref;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getIss() {
        return iss;
    }
    public void setIss(String iss) {
        this.iss = iss;
    }
    public String getSub() {
        return sub;
    }
    public void setSub(String sub) {
        this.sub = sub;
    }
    public Long getIat() {
        return iat;
    }
    public void setIat(Long iat) {
        this.iat = iat;
    }
    public String getMark() {
        return mark;
    }
    public void setMark(String mark) {
        this.mark = mark;
    }
    public Long getExp() {
        return exp;
    }
    public void setExp(Long exp) {
        this.exp = exp;
    }
    public String getRef() {
        return ref;
    }
    public void setRef(String ref) {
        this.ref = ref;
    }
    
}
