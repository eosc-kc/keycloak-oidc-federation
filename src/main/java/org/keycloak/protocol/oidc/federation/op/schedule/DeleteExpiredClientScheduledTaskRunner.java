package org.keycloak.protocol.oidc.federation.op.schedule;

import org.jboss.logging.Logger;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.timer.ScheduledTask;

public class DeleteExpiredClientScheduledTaskRunner implements ScheduledTask {

    private static final Logger logger = Logger.getLogger(DeleteExpiredClientScheduledTaskRunner.class);

    private String id;
    private String realmId;

    public DeleteExpiredClientScheduledTaskRunner(String id, String realmId) {
        this.id = id;
        this.realmId = realmId;
    }

    @Override
    public void run(KeycloakSession session) {
        RealmModel realm = session.realms().getRealm(realmId);
        if (realm != null) {
            boolean removed = realm.removeClient(id);
            logger.info(" Client with id = " + id + " removed with status = " + removed);
        }
    }

}
