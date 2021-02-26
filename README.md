This is a keycloak plugin to add support for OIDC federations.

It implements the [OpenID Connect Federation 1.0.Specification (draft 13)](https://openid.net/specs/openid-connect-federation-1_0.html).

This plugin supports OpenID Connect Federation (OP and RP) providers.
Version 1.0.0 supports explicit registration for both OP and RP. 

## Building

A 
`mvn clean install`
should be sufficient in order to generate the plugin's jar file.

## Installing

It should be installed as a keycloak extension on a existing keycloak instance, meaning that it should be deployed as a wildfly module (use the provided module.xml file), and also be added as a dependency in `keycloak-services` module xml, by adding the entry
`<module name="org.keycloak.keycloak-oidc-federation" services="import"/>` 
