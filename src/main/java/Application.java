import config.AppConfig;
import config.DatabaseConfig;
import init.DatabaseInitializer;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        DatabaseConfig databaseConfig = new DatabaseConfig(appConfig.connectionManager());
        databaseConfig.init();
        try {
            Janggi janggi = new Janggi(appConfig.boardRepository());
            janggi.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
