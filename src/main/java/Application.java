import controller.Controller;

import repository.DBConnection;
import repository.GameDao;
import repository.GameRepository;
import repository.H2DBConnection;
import repository.PieceDao;
import view.InputView;
import view.OutputView;

import java.sql.SQLException;

public class Application {
    public static void main(String[] args) throws SQLException {

        String dbUrl = "jdbc:h2:./janggi;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";

        InputView inputView = new InputView();
        OutputView outputView = new OutputView();

        DBConnection dbConnection = new H2DBConnection(dbUrl);
        GameRepository gameRepository = new GameRepository(dbConnection, new GameDao(), new PieceDao());

        Controller controller = new Controller(inputView, outputView, gameRepository);
        controller.run();
    }
}
