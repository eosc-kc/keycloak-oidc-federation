package org.keycloak.protocol.oidc.federation.common.beans;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.keycloak.protocol.oidc.federation.common.exceptions.MetadataPolicyCombinationException;
import org.keycloak.protocol.oidc.federation.common.exceptions.MetadataPolicyException;
import org.keycloak.protocol.oidc.federation.common.helpers.AddDeserializer;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public abstract class AbstractPolicy<T> {

    protected Set<T> subset_of;
    protected Set<T> one_of;
    protected Set<T> superset_of;
    @JsonDeserialize(using = AddDeserializer.class)
    protected Set<T> add;
    protected Boolean essential;
    protected Map<String, T> otherClaims = new HashMap<>();

    protected AbstractPolicy() {

    }

    protected AbstractPolicy<T> combinePolicyCommon(AbstractPolicy<T> inferior)  throws MetadataPolicyCombinationException{

        // combine subset_of
        if (inferior.getSubset_of() != null && this.subset_of != null) {
            this.subset_of = this.subset_of.stream().filter(inferior.getSubset_of()::contains).collect(Collectors.toSet());
            if (this.subset_of.isEmpty()) {
                this.subset_of = null;
            }
        } else if (inferior.getSubset_of() != null) {
            this.subset_of = inferior.getSubset_of();
        }
        // combine one_of
        if (inferior.getOne_of() != null && this.one_of != null) {
            this.one_of = this.one_of.stream().filter(inferior.getOne_of()::contains).collect(Collectors.toSet());
            if (this.one_of.isEmpty()) {
                this.one_of = null;
            }
        } else if (inferior.getOne_of() != null) {
            this.one_of = inferior.getOne_of();
        }
        // combine superset_of
        if (inferior.getSuperset_of() != null && this.superset_of != null) {
            this.superset_of = this.superset_of.stream().filter(inferior.getSuperset_of()::contains).collect(Collectors.toSet());
            if (this.superset_of.isEmpty()) {
                this.superset_of = null;
            }
        } else if (inferior.getSuperset_of() != null) {
            this.superset_of = inferior.getSuperset_of();
        }
        // combine add
        if (this.add == null) {
            this.add = inferior.getAdd();
        } else if (inferior.getAdd() != null) {
            this.add.addAll(inferior.getAdd());
        }
        //combine essential
        if (this.essential != null || inferior.getEssential() != null) {
            this.essential = this.essential == null || inferior.getEssential() == null || this.essential
                || inferior.getEssential();
        }
        return this;
    }
    
    protected boolean isNotAcceptedCombination(Object defaultValue, Object value) {
        return (this.add != null && (defaultValue != null || value != null || this.one_of != null
            || this.subset_of != null || this.superset_of != null)) || (defaultValue != null && value != null)
            || (this.one_of != null && (this.subset_of != null || this.superset_of != null))
            || (this.subset_of != null && this.superset_of != null && !this.subset_of.containsAll(this.superset_of));
    }

    public Set<T> getSubset_of() {
        return subset_of;
    }

    public void setSubset_of(Set<T> subset_of) {
        this.subset_of = subset_of;
    }

    public Set<T> getOne_of() {
        return one_of;
    }

    public void setOne_of(Set<T> one_of) {
        this.one_of = one_of;
    }

    public Set<T> getSuperset_of() {
        return superset_of;
    }

    public void setSuperset_of(Set<T> superset_of) {
        this.superset_of = superset_of;
    }

    public Set<T> getAdd() {
        return add;
    }

    public void setAdd(Set<T> add) {
        this.add = add;
    }

    public Boolean getEssential() {
        return essential;
    }

    public void setEssential(Boolean essential) {
        this.essential = essential;
    }

    @JsonAnyGetter
    public Map<String, T> getOtherClaims() {
        return otherClaims;
    }

    @JsonAnySetter
    public void setOtherClaims(Map<String, T> otherClaims) {
        this.otherClaims = otherClaims;
    }

}
