package org.keycloak.protocol.oidc.federation.op.schedule;

import org.jboss.logging.Logger;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.services.ServicesLogger;
import org.keycloak.services.scheduled.ScheduledTaskRunner;
import org.keycloak.timer.ScheduledTask;
import org.keycloak.timer.TimerProvider;

public class OIDCFedScheduledTaskRunner implements Runnable { 
    
    private static final Logger logger = Logger.getLogger(OIDCFedScheduledTaskRunner.class);
    
    private final KeycloakSessionFactory sessionFactory;
    private final ScheduledTask task;
    private final String taskName;

    public OIDCFedScheduledTaskRunner(KeycloakSessionFactory sessionFactory, ScheduledTask task,String taskName) {
        this.sessionFactory = sessionFactory;
        this.task = task;
        this.taskName = taskName;
    }
    
    @Override
    public void run() {
        KeycloakSession session = sessionFactory.create();
        try {
            session.getTransactionManager().begin();
            TimerProvider timer = session.getProvider(TimerProvider.class);
            task.run(session);
            timer.cancelTask(taskName);
            session.getTransactionManager().commit();
            
            logger.debug("Executed and canceled scheduled task " + task.getClass().getSimpleName());
        } catch (Throwable t) {
            ServicesLogger.LOGGER.failedToRunScheduledTask(t, task.getClass().getSimpleName());

            session.getTransactionManager().rollback();
        } finally {
            try {
                session.close();
            } catch (Throwable t) {
                ServicesLogger.LOGGER.failedToCloseProviderSession(t);
            }
        }
    }

}
