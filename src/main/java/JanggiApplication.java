import dao.BoardDao;
import dao.JanggiGameDao;
import db.ConfigLoader;
import db.DbBootstrap;
import db.DbConnectionFactory;
import db.TransactionExecutor;
import repository.JanggiGameRepository;
import service.JanggiGamePlayService;
import service.JanggiGameSetupService;
import view.InputView;
import view.OutputView;

public class JanggiApplication {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        OutputView outputView = new OutputView();
        JanggiGameRepository janggiGameRepository = getJanggiGameRepository();

        JanggiGameSetupService setupService = new JanggiGameSetupService(janggiGameRepository);
        JanggiGamePlayService playService = new JanggiGamePlayService(janggiGameRepository);

        JanggiGameRunner janggiGame = new JanggiGameRunner(
                inputView, outputView, setupService, playService
        );

        janggiGame.run();
    }

    private static JanggiGameRepository getJanggiGameRepository() {
        ConfigLoader configLoader = new ConfigLoader();
        DbConnectionFactory connectionFactory = new DbConnectionFactory(configLoader);
        DbBootstrap dbBootstrap = new DbBootstrap(connectionFactory);
        dbBootstrap.initialize();

        TransactionExecutor transactionExecutor = new TransactionExecutor(connectionFactory);

        JanggiGameDao janggiGameDao = new JanggiGameDao(connectionFactory);
        BoardDao boardDao = new BoardDao(connectionFactory, transactionExecutor);

        return new JanggiGameRepository(
                janggiGameDao, boardDao, transactionExecutor
        );
    }
}