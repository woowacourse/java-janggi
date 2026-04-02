import config.AppConfig;
import init.DatabaseInitializer;

public class Application {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        DatabaseInitializer databaseInitializer = appConfig.databaseInitializer();
        databaseInitializer.init();

        try {
            Janggi janggi = new Janggi(appConfig.gameService());
            janggi.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
