package database;

import org.h2.tools.Server;

public class H2ConsoleStarter {
    public static void start() {
        try {
            Server.createWebServer(
                    "-web",
                    "-webAllowOthers",
                    "-webPort",
                    "8082"
            ).start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}