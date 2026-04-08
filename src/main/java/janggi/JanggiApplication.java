package janggi;

import janggi.config.AppConfig;
import janggi.controller.JanggiController;
import janggi.infra.ConnectionProvider;
import janggi.infra.DbProperties;

public class JanggiApplication {

    public static void main(String[] args) {
        DbProperties dbProperties = new DbProperties();
        AppConfig appConfig = new AppConfig(dbProperties);

        ConnectionProvider provider = appConfig.connectionProvider();
        Runtime.getRuntime()
                .addShutdownHook(new Thread(provider::dispose));

        JanggiController controller = appConfig.janggiController();
        controller.run();
    }
}
