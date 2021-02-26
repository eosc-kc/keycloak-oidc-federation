package org.keycloak.protocol.oidc.federation.op.tasks;

import org.jboss.logging.Logger;
import org.keycloak.common.util.Time;
import org.keycloak.models.KeycloakSession;
import org.keycloak.protocol.oidc.federation.common.helpers.FedUtils;
import org.keycloak.protocol.oidc.federation.op.schedule.DeleteExpiredClientScheduledTaskRunner;
import org.keycloak.protocol.oidc.federation.op.schedule.OIDCFedScheduledTaskRunner;
import org.keycloak.timer.TimerProvider;

public class ClientExpiryTasks implements ClientExpiryTasksI {

    private static final Logger logger = Logger.getLogger(ClientExpiryTasks.class);

    private KeycloakSession session;

    protected ClientExpiryTasks(KeycloakSession session) {
        this.session = session;
    }


    @Override
    public void scheduleTask(String id, String realmId, long expiresAt) {
        TimerProvider timer = session.getProvider(TimerProvider.class);
        long interval =expiresAt - Long.valueOf(Time.currentTime())  ;
        //if client has expired already delete it now
        if (interval <=0) 
            interval =1;
        logger.info("Client with id= " + id + " will be deleted at " + expiresAt);
        String taskName = FedUtils.CLIENT_TASK_NAME + id + "." + realmId;
        timer.schedule(new OIDCFedScheduledTaskRunner(session.getKeycloakSessionFactory(),
            new DeleteExpiredClientScheduledTaskRunner(id, realmId), taskName), interval, taskName);

    }

    @Override
    public void close() {

    }



}
