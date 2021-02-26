
var keycloak_module = angular.module('keycloak');

keycloak_module.config(function($routeProvider) {

    $routeProvider
        .when('/realms/:realm/client-registration/oidc-federation-settings', {
            templateUrl : resourceUrl + '/partials/realm-oidc-federation-settings.html',
            resolve : {
                realm : function(RealmLoader) {
                    return RealmLoader();
                },
                configuration : function(ConfigurationLoader) {
                    return ConfigurationLoader();
                }
            },
            controller : 'RealmOidcFedSettingsCtrl'
        })
        
} );



keycloak_module.controller('RealmOidcFedSettingsCtrl', function($scope, $route, Realm, realm, configuration, Configuration, Dialog, Notifications,TimeUnit2) {
    $scope.realm = realm;
    $scope.realm.configuration = configuration;
    $scope.saved=true;
    if ($scope.realm.configuration.authorityHints === undefined) {
    	$scope.realm.configuration.authorityHints = [];    
    	$scope.saved=false;
    }
    if ($scope.realm.configuration.trustAnchors === undefined) {
    	$scope.realm.configuration.trustAnchors = [];    
    }
    if ($scope.realm.configuration.expirationTime === undefined) {
    	$scope.realm.configuration.expirationTime = 3600;    
    }
    $scope.realm.configuration.expirationTime = TimeUnit2.asUnit(realm.configuration.expirationTime);
    if ($scope.realm.configuration.registrationType === undefined) {
    	$scope.realm.configuration.registrationType = "both";    
    }
    
    $scope.registrationTypeList = [
        "both",
        "automatic",
        "explicit"
    ];
    var oldCopy = angular.copy($scope.realm.configuration);
    $scope.newAuthorityHint = "";
    $scope.newTrustAnchor= "";
    $scope.changed=false;
    
    $scope.$watch('realm', function() {
        if (!angular.equals($scope.realm.configuration, oldCopy)) {
            $scope.changed = true;
        }
    }, true);
    
    $scope.addAuthorityHint = function() {
    	$scope.realm.configuration.authorityHints.push($scope.newAuthorityHint);
        $scope.newAuthorityHint = "";
    }
    
    $scope.deleteAuthorityHint = function(index) {
    	$scope.realm.configuration.authorityHints.splice(index, 1);
    }
    
    $scope.addTrustAnchor = function() {
    	$scope.realm.configuration.trustAnchors.push($scope.newTrustAnchor);
        $scope.newTrustAnchor = "";
    }
    
    $scope.deleteTrustAnchor = function(index) {
    	$scope.realm.configuration.trustAnchors.splice(index, 1);
    }    
    
    $scope.save = function() {
    	if ($scope.newAuthorityHint && $scope.newAuthorityHint.length > 0) {
             $scope.addAuthorityHint();
        }
    	if ($scope.newTrustAnchor && $scope.newTrustAnchor.length > 0) {
            $scope.addTrustAnchor();
        }

    	if (!$scope.realm.configuration.authorityHints || $scope.realm.configuration.authorityHints.length == 0 || !$scope.realm.configuration.trustAnchors || $scope.realm.configuration.trustAnchors.length == 0) {
             Notifications.error("You must specify at least one authority hint and one trust anchor");
        } else {
        	$scope.realm.configuration.expirationTime = $scope.realm.configuration.expirationTime.toSeconds();
    	    Configuration.save({
                realm : realm.realm
            }, $scope.realm.configuration, function(data, headers) {
                $route.reload();
                Notifications.success("Your changes have been saved.");
           });
        }
    };
    
    $scope.reset = function() {
    	$scope.realm.configuration = angular.copy(oldCopy);
        $scope.changed = false;
    };
    
    $scope.deleteConfiguration = function () {
        Dialog.confirmDelete($scope.realm.realm, 'OIDC federation configuration of', function () {
        	Configuration.remove({
                realm: realm.realm
            }, function () {
            	 $route.reload();
            	 Notifications.success("OIDC federation configuration for this realm has been deleted.");
            });
        });
    };
});

keycloak_module.controller('RealmIdentityProviderCtrl', function($scope, $filter, $upload, $http, $route, realm, instance, providerFactory, IdentityProvider, serverInfo, authFlows, $location, Notifications, Dialog) {
    $scope.realm = angular.copy(realm);

    $scope.initSamlProvider = function() {
        $scope.nameIdFormats = [
            /*
            {
                format: "urn:oasis:names:tc:SAML:2.0:nameid-format:transient",
                name: "Transient"
            },
            */
            {
                format: "urn:oasis:names:tc:SAML:2.0:nameid-format:persistent",
                name: "Persistent"

            },
            {
                format: "urn:oasis:names:tc:SAML:1.1:nameid-format:emailAddress",
                name: "Email"

            },
            {
                format: "urn:oasis:names:tc:SAML:2.0:nameid-format:kerberos",
                name: "Kerberos"

            },
            {
                format: "urn:oasis:names:tc:SAML:1.1:nameid-format:X509SubjectName",
                name: "X.509 Subject Name"

            },
            {
                format: "urn:oasis:names:tc:SAML:1.1:nameid-format:WindowsDomainQualifiedName",
                name: "Windows Domain Qualified Name"

            },
            {
                format: "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified",
                name: "Unspecified"

            }
        ];
        $scope.signatureAlgorithms = [
            "RSA_SHA1",
            "RSA_SHA256",
            "RSA_SHA512",
            "DSA_SHA1"
        ];
        $scope.xmlKeyNameTranformers = [
            "NONE",
            "KEY_ID",
            "CERT_SUBJECT"
        ];
        $scope.principalTypes = [
            {
                type: "SUBJECT",
                name: "Subject NameID"

            },
            {
                type: "ATTRIBUTE",
                name: "Attribute [Name]"

            },
            {
                type: "FRIENDLY_ATTRIBUTE",
                name: "Attribute [Friendly Name]"

            }
        ];
        if (instance && instance.alias) {

        } else {
            $scope.identityProvider.config.nameIDPolicyFormat = $scope.nameIdFormats[0].format;
            $scope.identityProvider.config.principalType = $scope.principalTypes[0].type;
            $scope.identityProvider.config.signatureAlgorithm = $scope.signatureAlgorithms[1];
            $scope.identityProvider.config.samlXmlKeyNameTranformer = $scope.xmlKeyNameTranformers[1];
        }
    }

    $scope.hidePassword = true;
    $scope.fromUrl = {
        data: ''
    };

    if (instance && instance.alias) {
        $scope.identityProvider = angular.copy(instance);
        $scope.newIdentityProvider = false;
        for (var i in serverInfo.identityProviders) {
            var provider = serverInfo.identityProviders[i];

            if (provider.id == instance.providerId) {
                $scope.provider = provider;
            }
        }
        //////////////////////////////////////////////////oidc federation//////////////////////////////////////////////////
        $scope.authorityHintsList =  angular.fromJson($scope.identityProvider.config.authorityHints);
        $scope.trustAnchorIdsList =  angular.fromJson($scope.identityProvider.config.trustAnchorIds);
        //////////////////////////////////////////////////oidc federation//////////////////////////////////////////////////
    } else {
        $scope.identityProvider = {};
        $scope.identityProvider.config = {};
        $scope.identityProvider.alias = providerFactory.id;
        $scope.identityProvider.providerId = providerFactory.id;
        //////////////////////////////////////////////////oidc federation//////////////////////////////////////////////////
        if ("oidc-federation" === providerFactory.id) {
        	$scope.identityProvider.enabled = false;
        } else {
            $scope.identityProvider.enabled = true;
        }
        //////////////////////////////////////////////////oidc federation//////////////////////////////////////////////////
        $scope.identityProvider.authenticateByDefault = false;
        $scope.identityProvider.firstBrokerLoginFlowAlias = 'first broker login';
        $scope.identityProvider.config.useJwksUrl = 'true';
        $scope.identityProvider.config.syncMode = 'IMPORT';
         //////////////////////////////////////////////////oidc federation//////////////////////////////////////////////////
        $scope.authorityHintsList = [];
        $scope.trustAnchorIdsList = [];
        //////////////////////////////////////////////////oidc federation//////////////////////////////////////////////////
        $scope.newIdentityProvider = true;
    }

    $scope.changed = $scope.newIdentityProvider;

    $scope.$watch('identityProvider', function() {
        if (!angular.equals($scope.identityProvider, instance)) {
            $scope.changed = true;
        }
    }, true);


    $scope.serverInfo = serverInfo;

    $scope.allProviders = angular.copy(serverInfo.identityProviders);
    
    $scope.configuredProviders = angular.copy(realm.identityProviders);

    removeUsedSocial();
    
    $scope.authFlows = [];
    for (var i=0 ; i<authFlows.length ; i++) {
        if (authFlows[i].providerId == 'basic-flow') {
            $scope.authFlows.push(authFlows[i]);
        }
    }

    $scope.postBrokerAuthFlows = [];
    var emptyFlow = { alias: "" };
    $scope.postBrokerAuthFlows.push(emptyFlow);
    for (var i=0 ; i<$scope.authFlows.length ; i++) {
        $scope.postBrokerAuthFlows.push($scope.authFlows[i]);
    }
    
    if (!$scope.identityProvider.postBrokerLoginFlowAlias) {
        $scope.identityProvider.postBrokerLoginFlowAlias = $scope.postBrokerAuthFlows[0].alias;
    }

    $scope.$watch(function() {
        return $location.path();
    }, function() {
        $scope.path = $location.path().substring(1).split("/");
    });


    $scope.files = [];
    $scope.importFile = false;
    $scope.importUrl = false;

    $scope.onFileSelect = function($files) {
        $scope.importFile = true;
        $scope.files = $files;
    };

    $scope.clearFileSelect = function() {
        $scope.importUrl = false;
        $scope.importFile = false;
        $scope.files = null;
    };

    var setConfig = function(data) {
        for (var key in data) {
            $scope.identityProvider.config[key] = data[key];
        }
    }

    $scope.uploadFile = function() {
        if (!$scope.identityProvider.alias) {
            Notifications.error("You must specify an alias");
            return;
        }
        var input = {
            providerId: providerFactory.id
        }
        //$files: an array of files selected, each file has name, size, and type.
        for (var i = 0; i < $scope.files.length; i++) {
            var $file = $scope.files[i];
            $scope.upload = $upload.upload({
                url: authUrl + '/admin/realms/' + realm.realm + '/identity-provider/import-config',
                // method: POST or PUT,
                // headers: {'headerKey': 'headerValue'}, withCredential: true,
                data: input,
                file: $file
                /* set file formData name for 'Content-Desposition' header. Default: 'file' */
                //fileFormDataName: myFile,
                /* customize how data is added to formData. See #40#issuecomment-28612000 for example */
                //formDataAppender: function(formData, key, val){}
            }).progress(function(evt) {
                console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
            }).then(function(response) {
                setConfig(response.data);
                $scope.clearFileSelect();
                Notifications.success("The IDP metadata has been loaded from file.");
            }).catch(function() {
                Notifications.error("The file can not be uploaded. Please verify the file.");
            });
        }
    };

    $scope.importFrom = function() {
        if (!$scope.identityProvider.alias) {
            Notifications.error("You must specify an alias");
            return;
        }
        var input = {
            fromUrl: $scope.fromUrl.data,
            providerId: providerFactory.id
        }
        $http.post(authUrl + '/admin/realms/' + realm.realm + '/identity-provider/import-config', input)
            .then(function(response) {
                setConfig(response.data);
                $scope.fromUrl.data = '';
                $scope.importUrl = false;
                Notifications.success("Imported config information from url.");
            }).catch(function() {
                Notifications.error("Config can not be imported. Please verify the url.");
            });
    };
    $scope.$watch('fromUrl.data', function(newVal, oldVal){
        if ($scope.fromUrl.data && $scope.fromUrl.data.length > 0) {
            $scope.importUrl = true;
        } else{
            $scope.importUrl = false;
        }
    });

    $scope.$watch('configuredProviders', function(configuredProviders) {
        if (configuredProviders) {
            $scope.configuredProviders = angular.copy(configuredProviders);

            for (var j = 0; j < configuredProviders.length; j++) {
                var configProvidedId = configuredProviders[j].providerId;

                for (var i in $scope.allProviders) {
                    var provider = $scope.allProviders[i];
                    if (provider.id == configProvidedId) {
                        configuredProviders[j].provider = provider;
                    }
                }
            }
            $scope.configuredProviders = angular.copy(configuredProviders);
        }
    }, true);

    $scope.callbackUrl = authServerUrl + "/realms/" + realm.realm + "/broker/";

    $scope.addProvider = function(provider) {
        $location.url("/create/identity-provider/" + realm.realm + "/" + provider.id);
    };

    $scope.save = function() {
    	//////////////////////////////////////////////////oidc federation//////////////////////////////////////////////////
    	if ("oidc-federation" === providerFactory.id) {
    	    if ($scope.newAuthorityHint && $scope.newAuthorityHint.length > 0) {
                $scope.addAuthorityHint();
            }
   	        if ($scope.newTrustAnchor && $scope.newTrustAnchor.length > 0) {
               $scope.addTrustAnchor();
            } 
   	
            $scope.identityProvider.config.authorityHints = angular.toJson($scope.authorityHintsList);
            $scope.identityProvider.config.trustAnchorIds = angular.toJson($scope.trustAnchorIdsList);
    	}
    	//////////////////////////////////////////////////oidc federation//////////////////////////////////////////////////
        if ($scope.newIdentityProvider) {
            if (!$scope.identityProvider.alias) {
                Notifications.error("You must specify an alias");
                return;
            }
            //////////////////////////////////////////////////oidc federation//////////////////////////////////////////////////
            if ("oidc-federation" === providerFactory.id) {
            	$scope.identityProvider.config.clientId = authServerUrl + "/realms/" + realm.realm + "/relying-party/" + $scope.identityProvider.alias ;
            }
            //////////////////////////////////////////////////oidc federation//////////////////////////////////////////////////
            IdentityProvider.save({
                realm: $scope.realm.realm, alias: ''
            }, $scope.identityProvider, function () {
                $location.url("/realms/" + realm.realm + "/identity-provider-settings/provider/" + $scope.identityProvider.providerId + "/" + $scope.identityProvider.alias);
                Notifications.success("The " + $scope.identityProvider.alias + " provider has been created.");
            });
        } else {
            IdentityProvider.update({
                realm: $scope.realm.realm,
                alias: $scope.identityProvider.alias
            }, $scope.identityProvider, function () {
                $route.reload();
                Notifications.success("The " + $scope.identityProvider.alias + " provider has been updated.");
            });
        }
    };

    $scope.cancel = function() {
        if ($scope.newIdentityProvider) {
            $location.url("/realms/" + realm.realm + "/identity-provider-settings");
        } else {
            $route.reload();
        }
    };


    $scope.reset = function() {
        $scope.identityProvider = {};
        $scope.configuredProviders = angular.copy($scope.realm.identityProviders);
    };
    
    $scope.showPassword = function(flag) {
        $scope.hidePassword = flag;
    };

    $scope.removeIdentityProvider = function(identityProvider) {
        Dialog.confirmDelete(identityProvider.alias, 'provider', function() {
            IdentityProvider.remove({
                realm : realm.realm,
                alias : identityProvider.alias
            }, function() {
                $route.reload();
                Notifications.success("The identity provider has been deleted.");
            });
        });
    };
    
    // KEYCLOAK-5932: remove social providers that have already been defined
    function removeUsedSocial() {
        var i = $scope.allProviders.length;
        while (i--) {
            if ($scope.allProviders[i].groupName !== 'Social') continue;
            if ($scope.configuredProviders != null) {
                for (var j = 0; j < $scope.configuredProviders.length; j++) {
                    if ($scope.configuredProviders[j].providerId === $scope.allProviders[i].id) {
                        $scope.allProviders.splice(i, 1);
                        break;
                    }
                }
            }
        }
    };
    
    //////////////////////////////////////////////////oidc federation//////////////////////////////////////////////////
    $scope.register = function() {
        var url = authServerUrl + "/realms/" + realm.realm + "/relying-party/" + $scope.identityProvider.alias +"/explicit-registration" ;
    	$http.get(url).then(function(response) {
    		$scope.identityProvider = angular.fromJson(response.data);
    		$scope.authorityHintsList =  angular.fromJson($scope.identityProvider.config.authorityHints);
            $scope.trustAnchorIdsList =  angular.fromJson($scope.identityProvider.config.trustAnchorIds);
            Notifications.success("Successful explicit registration to Federation OP");
    	}).catch(function(response) {
            Notifications.error(response.data.message);
        });
    };
    
    $scope.addAuthorityHint = function() {
    	$scope.authorityHintsList.push($scope.newAuthorityHint);
        $scope.newAuthorityHint = "";
    }
    
    $scope.deleteAuthorityHint = function(index) {
    	$scope.authorityHintsList.splice(index, 1);
    }
    
    $scope.addTrustAnchor = function() {
    	$scope.trustAnchorIdsList.push($scope.newTrustAnchor);
        $scope.newTrustAnchor = "";
    }
    
    $scope.deleteTrustAnchor = function(index) {
    	$scope.trustAnchorIdsList.splice(index, 1);
    }    

});



keycloak_module.controller('ClientRegPoliciesCtrl', function($scope, realm, clientRegistrationPolicyProviders, policies, Dialog, Notifications, Components, $route, $location, $compile) {
    $scope.realm = realm;
    $scope.providers = clientRegistrationPolicyProviders;
    $scope.anonPolicies = [];
    $scope.authPolicies = [];
    for (var i=0 ; i<policies.length ; i++) {
        var policy = policies[i];
        if (policy.subType === 'anonymous') {
            $scope.anonPolicies.push(policy);
        } else if (policy.subType === 'authenticated') {
            $scope.authPolicies.push(policy);
        } else {
            throw 'subType is required for clientRegistration policy component!';
        }
    }

    $scope.addProvider = function(authType, provider) {
        console.log('Add provider: authType ' + authType + ', providerId: ' + provider.id);
        $location.url("/realms/" + realm.realm + "/client-registration/client-reg-policies/create/" + authType + '/' + provider.id);
    };

    $scope.getInstanceLink = function(instance) {
        return "/realms/" + realm.realm + "/client-registration/client-reg-policies/" + instance.providerId + "/" + instance.id;
    }

    $scope.removeInstance = function(instance) {
        Dialog.confirmDelete(instance.name, 'client registration policy', function() {
            Components.remove({
                realm : realm.realm,
                componentId : instance.id
            }, function() {
                $route.reload();
                Notifications.success("The policy has been deleted.");
            });
        });
    };
    
    $scope.realm = realm;
    var divElement = angular.element(document.querySelector(".nav.nav-tabs.nav-tabs-pf"));
    var appendHtml = $compile("<li><a href='#/realms/{{realm.realm}}/client-registration/oidc-federation-settings'>{{:: 'realm-tab-oidc-federation' | translate}}</a></li>")($scope);
    divElement.append(appendHtml);

});



keycloak_module.controller('ClientInitialAccessCtrl', function($scope, realm, clientInitialAccess, ClientInitialAccess, Dialog, Notifications, $route, $location, $compile) {
    $scope.realm = realm;
    $scope.clientInitialAccess = clientInitialAccess;

    $scope.remove = function(id) {
        Dialog.confirmDelete(id, 'initial access token', function() {
            ClientInitialAccess.remove({ realm: realm.realm, id: id }, function() {
                Notifications.success("The initial access token was deleted.");
                $route.reload();
            });
        });
    }
    
    $scope.realm = realm;
    var divElement = angular.element(document.querySelector(".nav.nav-tabs.nav-tabs-pf"));
    var appendHtml = $compile("<li><a href='#/realms/{{realm.realm}}/client-registration/oidc-federation-settings'>{{:: 'realm-tab-oidc-federation' | translate}}</a></li>")($scope);
    divElement.append(appendHtml);
    
    
});






var keycloak_loaders = angular.module('keycloak.loaders');

keycloak_loaders.factory('ConfigurationLoader', function(Loader, Configuration, $route) {
    return Loader.get(Configuration, function() {
        return {
            realm: $route.current.params.realm
        }
    });
});



var keycloak_services = angular.module('keycloak.services');

keycloak_services.factory('Configuration', function($resource) {
    return $resource(authUrl + '/realms/:realm/oidc-federation/configuration', {
        realm : '@realm',
    });
});


