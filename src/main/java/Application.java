import controller.JanggiController;
import repository.BoardLocationDAO;
import repository.Connector;
import repository.DAOService;
import repository.GameDAO;
import repository.MySQLConnector;
import repository.PlayerDAO;
import view.InputView;
import view.OutputView;

public final class Application {

    public static void main(String[] args) {
        final InputView inputView = new InputView();
        final OutputView outputView = new OutputView();
        try {
            final Connector connector = new MySQLConnector();
            final DAOService service = new DAOService(
                    new GameDAO(connector),
                    new BoardLocationDAO(connector),
                    new PlayerDAO(connector)
            );

            final JanggiController controller = new JanggiController(inputView, outputView, service);
            controller.run();
        } catch (RuntimeException e) {
            outputView.printError(e.getMessage());
        }
    }
}
