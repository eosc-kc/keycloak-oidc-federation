package org.keycloak.protocol.oidc.federation.common.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.keycloak.protocol.oidc.federation.common.exceptions.MetadataPolicyCombinationException;
import org.keycloak.protocol.oidc.federation.common.exceptions.MetadataPolicyException;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolicyList<T> extends AbstractPolicy<T> {

    private Set<T> value;
    @JsonProperty("default")
    private Set<T> defaultValue;

    public PolicyList() {

    }

    public PolicyList<T> combinePolicy(PolicyList<T> inferior) throws MetadataPolicyCombinationException {

        if (inferior == null)
            return this;
        // first check combination value with one_of, subset_of , superset_of
        if (notNullnotEqual(this.value, inferior.getValue()))
            throw new MetadataPolicyCombinationException("Could not combine two different values");
        if (this.value != null) {
            this.one_of = null;
            this.subset_of = null;
            this.superset_of = null;
            inferior.setOne_of(null);
            inferior.setSubset_of(null);
            inferior.setSuperset_of(null);
        }

        this.combinePolicyCommon(inferior);

        if (this.value == null) {
            if (inferior.getValue() != null && ((this.one_of != null && !this.one_of.containsAll(inferior.getValue()))
                || (this.subset_of != null && !this.subset_of.containsAll(inferior.getValue()))
                || (this.superset_of != null && !this.superset_of.containsAll(inferior.getValue()))))
                throw new MetadataPolicyCombinationException(
                    "Inferior value must be subset of one_of,subset_of and superset_of, if one of these exist ");
            this.value = inferior.getValue();
        }

        if (notNullnotEqual(this.defaultValue, inferior.getDefaultValue())) {
            throw new MetadataPolicyCombinationException("Could not construct two different values");
        } else if (this.defaultValue == null) {
            this.defaultValue = inferior.getDefaultValue();
        }

        if (illegalDefaultValueCombination())
            throw new MetadataPolicyCombinationException(
                "Not null default value must be subset of one_of,subset_of and superset of superset_of, if one of these exist ");

        if (isNotAcceptedCombination(this.defaultValue, this.value))
            throw new MetadataPolicyCombinationException("False Policy Type Combination exists");

        return this;
    }

    public PolicyList<T> policyTypeCombination() throws MetadataPolicyCombinationException {
        if (illegalDefaultValueCombination())
            throw new MetadataPolicyCombinationException(
                "Not null default value must be subset of one_of,subset_of and superset of superset_of, if one of these exist ");

        if (isNotAcceptedCombination(this.defaultValue, this.value))
            throw new MetadataPolicyCombinationException("False Policy Type Combination exists");

        if (this.value != null) {
            this.one_of = null;
            this.subset_of = null;
            this.superset_of = null;
        }

        return this;
    }

    private boolean illegalDefaultValueCombination() {
        return this.defaultValue != null && ((this.one_of != null && !this.one_of.containsAll(this.defaultValue))
            || (this.subset_of != null && !this.subset_of.containsAll(this.defaultValue))
            || (this.superset_of != null && !this.defaultValue.containsAll(this.superset_of)));
    }

    private boolean notNullnotEqual(Set<T> superiorValue, Set<T> inferiorValue) {
        return superiorValue != null && inferiorValue != null
            && !(superiorValue.size() == inferiorValue.size() && superiorValue.containsAll(inferiorValue));
    }

    public List<T> enforcePolicy(List<T> t, String name) throws MetadataPolicyException {
        
        if (t == null && this.essential != null && this.essential)
            throw new MetadataPolicyException(name + " must exist in rp");

        //add can only exist alone
        if (this.add != null) {
            if (t == null)
                t = new ArrayList<>();
            for (T val : this.add) {
                if (!t.contains(val))
                    t.add(val);
            }
            return t;
        }

        if (this.value != null) {
            return this.value.stream().collect(Collectors.toList());
        }
        
        if (this.defaultValue != null && t == null) {
            return this.defaultValue.stream().collect(Collectors.toList());
        }

        if (this.one_of != null && ((t != null && !this.one_of.containsAll(t)) || t == null))
            throw new MetadataPolicyException(
                name + " must have one values of " + StringUtils.join(this.one_of.toArray(), ","));
        if (this.superset_of != null && (t == null || !t.containsAll(this.superset_of)))
            throw new MetadataPolicyException(
                name + " values must be superset of " + StringUtils.join(this.superset_of.toArray(), ","));


        if (this.subset_of != null && t != null) {
            t.stream().filter(e -> this.subset_of.contains(e)).collect(Collectors.toList());
        }

        return t;
    }

    public Set<T> getValue() {
        return value;
    }

    public void setValue(Set<T> value) {
        this.value = value;
    }

    public Set<T> getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Set<T> defaultValue) {
        this.defaultValue = defaultValue;
    }

}
