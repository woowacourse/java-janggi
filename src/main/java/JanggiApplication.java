import controller.JanggiController;
import database.jdbc.DatabaseConnector;
import database.jdbc.JdbcGameDao;
import database.jdbc.JdbcPieceDao;
import database.service.GameService;
import view.ConsolePieceAppearance;
import view.InputView;
import view.OutputView;

public class JanggiApplication {
    public static void main(String[] args) {
        DatabaseConnector connector = new DatabaseConnector();
        JdbcGameDao jdbcGameDao = new JdbcGameDao(connector);
        JdbcPieceDao jdbcPieceDao = new JdbcPieceDao(connector);
        GameService gameService = new GameService(connector, jdbcGameDao, jdbcPieceDao);
        JanggiController janggiController = new JanggiController(new InputView(),
                new OutputView(new ConsolePieceAppearance()), gameService);
        janggiController.run();
    }
}
