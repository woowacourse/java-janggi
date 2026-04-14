import config.ConnectionFactory;
import config.DatabaseConfig;
import controller.JanggiController;
import dao.GameDao;
import mapper.BoardOutputMapper;
import transaction.TransactionTemplate;
import view.InputView;
import view.OutputView;

public class Application {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = DatabaseConfig.createConnectionFactory();

        GameDao gameDao = new GameDao(connectionFactory);
        TransactionTemplate transactionTemplate = new TransactionTemplate(connectionFactory);
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        BoardOutputMapper boardOutputMapper = new BoardOutputMapper();

        JanggiController janggiController = new JanggiController(
                inputView, outputView, boardOutputMapper, gameDao, transactionTemplate);
        janggiController.run();
    }
}
