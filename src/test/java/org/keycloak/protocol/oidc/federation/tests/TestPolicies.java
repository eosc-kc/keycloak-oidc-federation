package org.keycloak.protocol.oidc.federation.tests;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.keycloak.protocol.oidc.federation.common.beans.EntityStatement;
import org.keycloak.protocol.oidc.federation.common.beans.RPMetadata;
import org.keycloak.protocol.oidc.federation.common.beans.RPMetadataPolicy;
import org.keycloak.protocol.oidc.federation.common.exceptions.MetadataPolicyCombinationException;
import org.keycloak.protocol.oidc.federation.common.exceptions.MetadataPolicyException;
import org.keycloak.protocol.oidc.federation.common.helpers.MetadataPolicyUtils;
import org.keycloak.util.JsonSerialization;

public class TestPolicies {

    @Test
    public void combineMetadataPolicyInRPStatement() throws IOException, URISyntaxException, MetadataPolicyCombinationException, MetadataPolicyException {
        URL rpEntityStatement = getClass().getClassLoader().getResource("oidc/rpEntityStatement.json");
        byte [] content = Files.readAllBytes(Paths.get(rpEntityStatement.toURI()));
        EntityStatement statement = JsonSerialization.readValue(content, EntityStatement.class);
        URL policyTA = getClass().getClassLoader().getResource("oidc/policyTrustAnchor.json");
        byte [] contentpolicyTA = Files.readAllBytes(Paths.get(policyTA.toURI()));
        RPMetadataPolicy superiorPolicy = JsonSerialization.readValue(contentpolicyTA, RPMetadataPolicy.class);
        URL policyInter = getClass().getClassLoader().getResource("oidc/policyInter.json");
        byte [] contentpolicyInter  = Files.readAllBytes(Paths.get(policyInter.toURI()));
        RPMetadataPolicy inferiorPolicy = JsonSerialization.readValue(contentpolicyInter, RPMetadataPolicy.class);
        superiorPolicy = MetadataPolicyUtils.combineClientPOlicies(superiorPolicy, inferiorPolicy);
        statement = MetadataPolicyUtils.applyPoliciesToRPStatement(statement, superiorPolicy);

        //check statement for proper rp policy data
        Assert.assertNotNull(superiorPolicy.getScope());
        Assert.assertNotNull(superiorPolicy.getScope().getSubset_of());
        assertNames(superiorPolicy.getScope().getSubset_of(), "openid","eduperson");
        Assert.assertNotNull(superiorPolicy.getScope().getSuperset_of());
        assertNames(superiorPolicy.getScope().getSuperset_of(), "openid");
        assertEquals("openid",superiorPolicy.getScope().getDefaultValue());
        Assert.assertNotNull(superiorPolicy.getApplication_type());
        assertEquals("web",superiorPolicy.getApplication_type().getValue());
        Assert.assertNotNull(superiorPolicy.getContacts());
        Assert.assertNotNull(superiorPolicy.getContacts().getAdd());
        assertNames(superiorPolicy.getContacts().getAdd(), "helpdesk@org.example.org","helpdesk@federation.example.org");
        Assert.assertNotNull(superiorPolicy.getId_token_signed_response_alg());
        Assert.assertNotNull(superiorPolicy.getId_token_signed_response_alg().getOne_of());
        assertNames(superiorPolicy.getId_token_signed_response_alg().getOne_of(), "ES384","ES256");
        assertEquals("ES256",superiorPolicy.getId_token_signed_response_alg().getDefaultValue());
        Assert.assertTrue(superiorPolicy.getId_token_signed_response_alg().getEssential());

        //check statement for proper rp data
        Assert.assertNotNull(statement.getMetadata());
        Assert.assertNotNull(statement.getMetadata().getRp());
        RPMetadata rp =statement.getMetadata().getRp();
        Assert.assertNotNull(rp.getRedirectUris());
        assertEquals("client RedirectUris size", 1, rp.getRedirectUris().size());
        assertEquals("https://127.0.0.1:4000/authz_cb/local", rp.getRedirectUris().get(0));
        assertEquals("web", rp.getApplicationType());
        Assert.assertNotNull(rp.getResponseTypes());
        assertEquals("ResponseTypes size", 1, rp.getResponseTypes().size());
        assertEquals("code", rp.getResponseTypes().get(0));
        Assert.assertNotNull(rp.getContacts());
        assertEquals("Contacts size", 3, rp.getContacts().size());
        assertContains( rp.getContacts(), "ops@example.com", "helpdesk@org.example.org" , "helpdesk@federation.example.org");
        assertEquals("client_secret_basic", rp.getTokenEndpointAuthMethod());
        assertEquals("ES384", rp.getIdTokenSignedResponseAlg());
        assertEquals("openid", rp.getScope());

    }

    @Test
    public void incorrectRPStatement() throws IOException, URISyntaxException, MetadataPolicyCombinationException {
        URL rpEntityStatement = getClass().getClassLoader().getResource("oidc/rpEntityStatement.json");
        byte [] content = Files.readAllBytes(Paths.get(rpEntityStatement.toURI()));
        EntityStatement statement = JsonSerialization.readValue(content, EntityStatement.class);
        //add scope address for being invalid rp metadata
        statement.getMetadata().getRp().setScope("address");
        URL policyTA = getClass().getClassLoader().getResource("oidc/policyTrustAnchor.json");
        byte [] contentpolicyTA = Files.readAllBytes(Paths.get(policyTA.toURI()));
        RPMetadataPolicy superiorPolicy = JsonSerialization.readValue(contentpolicyTA, RPMetadataPolicy.class);
        URL policyInter = getClass().getClassLoader().getResource("oidc/policyInter.json");
        byte [] contentpolicyInter  = Files.readAllBytes(Paths.get(policyInter.toURI()));
        RPMetadataPolicy inferiorPolicy = JsonSerialization.readValue(contentpolicyInter, RPMetadataPolicy.class);
        superiorPolicy = MetadataPolicyUtils.combineClientPOlicies(superiorPolicy, inferiorPolicy);

        //check that rp metadata is invalid due to policies
        boolean exceptionThrown = false;
        try {
            statement = MetadataPolicyUtils.applyPoliciesToRPStatement(statement, superiorPolicy);
        } catch (MetadataPolicyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            exceptionThrown = true;
        }
        Assert.assertTrue("RP metadata must be invalid due to enforcing policies", exceptionThrown);
    }

    private void assertNames(Set<String> actual, String... expected) {
        Arrays.sort(expected);
        String[] actualNames = actual.toArray(new String[actual.size()]);
        Arrays.sort(actualNames);
        Assert.assertArrayEquals("Expected: " + Arrays.toString(expected) + ", was: " + Arrays.toString(actualNames), expected, actualNames);
    }

    private void assertContains(List<String> actual, String... expected) {
        for (String exp : expected) {
            Assert.assertTrue(actual.contains(exp));
        }
    }


}
