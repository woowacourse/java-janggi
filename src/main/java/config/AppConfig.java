package config;

import console.GameConsole;
import repository.connector.Connector;
import repository.connector.MysqlConnector;
import repository.game_record.GameRecordRepository;
import repository.game_record.GameRecordRepositoryImpl;
import repository.move_record.MoveRecordRepository;
import repository.move_record.MoveRecordRepositoryImpl;
import service.GameService;
import view.InputView;
import view.OutputView;

public class AppConfig {

    private final DatabaseProperties databaseProperties = new DatabaseProperties();

    public GameConsole gameConsole() {
        return new GameConsole(gameService(), inputView(), outputView());
    }

    private GameService gameService() {
        return new GameService(moveRecordRepository(), gameRecordRepository());
    }

    private InputView inputView() {
        return new InputView();
    }

    private OutputView outputView() {
        return new OutputView();
    }

    private GameRecordRepository gameRecordRepository() {
        return new GameRecordRepositoryImpl(connector());
    }

    private MoveRecordRepository moveRecordRepository() {
        return new MoveRecordRepositoryImpl(connector());
    }

    private Connector connector() {
        return new MysqlConnector(databaseProperties);
    }
}
