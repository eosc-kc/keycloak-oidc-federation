package org.keycloak.protocol.oidc.federation.common.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.keycloak.crypto.KeyType;
import org.keycloak.crypto.KeyUse;
import org.keycloak.crypto.KeyWrapper;
import org.keycloak.jose.jwk.JSONWebKeySet;
import org.keycloak.jose.jwk.JWK;
import org.keycloak.jose.jwk.JWKBuilder;
import org.keycloak.models.KeycloakSession;
import org.keycloak.protocol.oidc.federation.common.beans.EntityStatement;
import org.keycloak.protocol.oidc.federation.common.exceptions.InternalServerErrorException;

public class FedUtils {

    private static String WELL_KNOWN_SUBPATH = ".well-known/openid-federation";
    public static final String SECRET_EXPIRES_AT="ClientSecretExpiresAt";
    public static final String CLIENT_TASK_NAME="DeleteExpiredClient";
    
    
    public static String getSelfSignedToken(String issuer) throws MalformedURLException, IOException {
        issuer = issuer.trim();
        if(!issuer.endsWith("/"))
            issuer += "/";
        return getContentFrom(new URL(issuer + WELL_KNOWN_SUBPATH));
    }
    
    public static String getSubordinateToken(String fedApiUrl, String issuer, String subject) throws MalformedURLException, UnsupportedEncodingException, IOException {
        return getContentFrom(new URL(fedApiUrl + "?iss="+urlEncode(issuer)+"&sub="+urlEncode(subject)));
    }
    
    
    private static String getContentFrom(URL url) throws IOException {
        StringBuffer content = new StringBuffer();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null)
            content.append(inputLine);
        return content.toString();
    }
    
    public static String getJoseContentFromPost(URL url,String body) throws IOException,InternalServerErrorException {
        StringBuffer content = new StringBuffer();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/jose");
        con.setDoOutput(true);
        OutputStream outStream = con.getOutputStream();
        OutputStreamWriter outStreamWriter = new OutputStreamWriter(outStream, "UTF-8");
        outStreamWriter.write(body);
        outStreamWriter.flush();
        outStreamWriter.close();
        outStream.close();
        //return null for error
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;   
       
        while ((inputLine = in.readLine()) != null)
            content.append(inputLine);
        //if an error occur
        if (con.getResponseCode()>= 300)
            throw new InternalServerErrorException(content.toString());
        return content.toString();
    }
    
    public static JSONWebKeySet getKeySet(KeycloakSession session) {
        List<JWK> keys = new LinkedList<>();
        for (KeyWrapper k : session.keys().getKeys(session.getContext().getRealm())) {
            if (k.getStatus().isEnabled() && k.getUse().equals(KeyUse.SIG) && k.getPublicKey() != null) {
                JWKBuilder b = JWKBuilder.create().kid(k.getKid()).algorithm(k.getAlgorithm());
                if (k.getType().equals(KeyType.RSA)) {
                    keys.add(b.rsa(k.getPublicKey(), k.getCertificate()));
                } else if (k.getType().equals(KeyType.EC)) {
                    keys.add(b.ec(k.getPublicKey()));
                }
            }
        }

        JSONWebKeySet keySet = new JSONWebKeySet();

        JWK[] k = new JWK[keys.size()];
        k = keys.toArray(k);
        keySet.setKeys(k);
        return keySet;
    }
    
    
    private static String urlEncode(String url) throws UnsupportedEncodingException {
        return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
    }

}
