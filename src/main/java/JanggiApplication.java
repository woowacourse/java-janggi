import config.MySqlConnector;
import controller.JanggiController;
import dao.JanggiDaoImpl;
import dao.PieceDaoImpl;
import service.JanngiService;
import view.InputView;
import view.OutputView;

public class JanggiApplication {
    public static void main(String[] args) {
        final MySqlConnector mySqlConnector = new MySqlConnector();
        final JanngiService janngiService = new JanngiService(new PieceDaoImpl(mySqlConnector), new JanggiDaoImpl(mySqlConnector));
        final JanggiController janggiController = new JanggiController(new InputView(), new OutputView(), janngiService);
        janggiController.run();
    }
}
