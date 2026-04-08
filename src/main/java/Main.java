import controller.GameConsole;
import repository.connector.Connector;
import repository.connector.MysqlConnector;
import repository.game.GameRepository;
import repository.game.GameRepositoryImpl;
import repository.move_record.MoveRecordRepository;
import repository.move_record.MoveRecordRepositoryImpl;
import service.GameService;
import view.InputView;
import view.OutputView;

public class Main {

    public static void main(String[] args) {
        Connector connector = new MysqlConnector();
        MoveRecordRepository moveRecordRepository = new MoveRecordRepositoryImpl(connector);
        GameRepository gameRepository = new GameRepositoryImpl(connector);

        GameConsole gameConsole = new GameConsole(
            new GameService(moveRecordRepository, gameRepository), new InputView(), new OutputView());
        gameConsole.run();
    }
}
