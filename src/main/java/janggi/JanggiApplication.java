package janggi;

import janggi.config.AppConfig;
import janggi.controller.JanggiController;
import janggi.infra.ConnectionProvider;
import janggi.infra.DbProperties;
import janggi.infra.PersistenceConfig;

public class JanggiApplication {

    public static void main(String[] args) {
        DbProperties dbProperties = new DbProperties();
        PersistenceConfig persistenceConfig = new PersistenceConfig(dbProperties);
        AppConfig appConfig = new AppConfig(persistenceConfig);

        ConnectionProvider provider = persistenceConfig.connectionProvider();
        registerShutdownHook(provider);

        JanggiController controller = appConfig.janggiController();
        controller.run();
    }

    private static void registerShutdownHook(ConnectionProvider connectionProvider) {
        Runtime.getRuntime()
                .addShutdownHook(new Thread(connectionProvider::dispose));
    }
}
