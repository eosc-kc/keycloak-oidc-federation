package org.keycloak.protocol.oidc.federation.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;
import org.keycloak.protocol.oidc.federation.common.TrustChain;
import org.keycloak.protocol.oidc.federation.common.processes.TrustChainProcessor;
import org.keycloak.protocol.oidc.federation.tests.scaffolding.configuration.YamlConfiguration;

public class TestTrustChains extends TestBase {

    
    
    @Test
    public void test1() throws Exception {
        
        String host = YamlConfiguration.getConfig().getHost();
        host = host.endsWith("/") ? host : host+"/";
        
        List<String> trustAnchors = Arrays.asList(host + "trustanchor1", host + "trustanchor2");

        TrustChainProcessor trustChainProcessor = new TrustChainProcessor();
        
        List<TrustChain> trustChains = trustChainProcessor.constructTrustChainsFromUrl(
            host + "intermediate1", 
            new HashSet<>(trustAnchors), true);
        
        assertNotNull(trustChains);
        assertTrue(trustChains.size()==3);
        
        String leafId = trustChains.get(0).getLeafId();
        for(TrustChain trustChain: trustChains) {
            assertEquals(trustChain.getLeafId(), leafId);
            assertTrue(trustAnchors.contains(trustChain.getTrustAnchorId()));
            for(int i = trustChain.getParsedChain().size()-1 ; i>0 ; i--)
                assertEquals(trustChain.getParsedChain().get(i).getSubject(), trustChain.getParsedChain().get(i-1).getIssuer());
            trustChainProcessor.validateTrustChain(trustChain);
        }
        
    }
    
    @Test
    public void test2() throws Exception {
        
        String host = YamlConfiguration.getConfig().getHost();
        host = host.endsWith("/") ? host : host+"/";
        
        List<String> trustAnchors = Arrays.asList(host + "trustanchor1", host + "trustanchor2");

        TrustChainProcessor trustChainProcessor = new TrustChainProcessor();
        
        List<TrustChain> trustChains = trustChainProcessor.constructTrustChainsFromUrl(
            host + "intermediate2", 
            new HashSet<>(trustAnchors), true);
        
        assertNotNull(trustChains);
        assertTrue(trustChains.size()==1);
        
        String leafId = trustChains.get(0).getLeafId();
        for(TrustChain trustChain: trustChains) {
            assertEquals(trustChain.getLeafId(), leafId);
            assertTrue(trustAnchors.contains(trustChain.getTrustAnchorId()));
            for(int i = trustChain.getParsedChain().size()-1 ; i>0 ; i--)
                assertEquals(trustChain.getParsedChain().get(i).getSubject(), trustChain.getParsedChain().get(i-1).getIssuer());
            trustChainProcessor.validateTrustChain(trustChain);
        }
    }
    
    
}
