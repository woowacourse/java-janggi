package database;

import java.sql.SQLException;
import org.h2.tools.Server;

public class H2ConsoleStarter {
    private static Server server;

    public static void start() {
        if (server != null && server.isRunning(false)) {
            return;
        }

        try {
            server = Server.createWebServer("-web", "-webAllowOthers", "-webPort", "8082").start();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}