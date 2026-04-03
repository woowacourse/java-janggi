package config;

import infra.DatabaseInitializer;
import service.GameService;

public class AppConfig {

    private final DatabaseConfig databaseConfig = new DatabaseConfig();
    private final RepositoryConfig repositoryConfig = new RepositoryConfig();
    private final ServiceConfig serviceConfig = new ServiceConfig();

    public GameService gameService() {
        return serviceConfig.gameService(
                repositoryConfig.boardRepository(),
                repositoryConfig.gameRoomRepository(),
                databaseConfig.dbExecutor()
        );
    }

    public DatabaseInitializer databaseInitializer() {
        return databaseConfig.databaseInitializer();
    }
}
