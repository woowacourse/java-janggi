package application;

import factory.JanggiBoardFactory;
import javax.sql.DataSource;
import persistence.datasource.DataSourceInitializer;
import persistence.datasource.H2DataSourceFactory;
import persistence.mapper.GameStateMapper;
import persistence.mapper.PieceStateMapper;
import persistence.repository.GameStateRepository;
import persistence.repository.JdbcGameStateRepository;

public final class GameInitializer {

    private final DataSource dataSource = new H2DataSourceFactory().create();

    public void initializeDatabase() {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer(dataSource);
        dataSourceInitializer.initialize();
    }

    public GamePersistenceService createGamePersistenceService() {
        GameStateRepository repository = new JdbcGameStateRepository(dataSource);
        GameStateMapper gameStateMapper = new GameStateMapper(new PieceStateMapper());
        JanggiBoardFactory boardFactory = new JanggiBoardFactory();
        return new GamePersistenceService(repository, gameStateMapper, boardFactory);
    }
}
