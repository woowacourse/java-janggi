package janggi.config;

import janggi.controller.JanggiController;
import janggi.dao.game.GameDao;
import janggi.dao.game.JdbcGameDao;
import janggi.dao.piece.JdbcPieceDao;
import janggi.dao.piece.PieceDao;
import janggi.infra.ConnectionProvider;
import janggi.infra.DbProperties;
import janggi.infra.transaction.TransactionExecutor;
import janggi.infra.transaction.TransactionExecutorImpl;
import janggi.service.JanggiService;
import janggi.view.InputView;
import janggi.view.OutputView;

public class AppConfig {

    private final DbProperties dbProperties;
    private ConnectionProvider connectionProvider;
    private TransactionExecutor transactionExecutor;

    private GameDao gameDao;
    private PieceDao pieceDao;

    private JanggiService janggiService;

    private OutputView outputView;
    private InputView inputView;

    private JanggiController janggiController;

    public AppConfig(DbProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    public ConnectionProvider connectionProvider() {
        if (connectionProvider == null) {
            connectionProvider = new ConnectionProvider(dbProperties);
        }

        return connectionProvider;
    }

    public TransactionExecutor transactionExecutor() {
        if (transactionExecutor == null) {
            transactionExecutor = new TransactionExecutorImpl(connectionProvider());
        }

        return transactionExecutor;
    }

    public GameDao gameDao() {
        if (gameDao == null) {
            gameDao = new JdbcGameDao();
        }

        return gameDao;
    }

    public PieceDao pieceDao() {
        if (pieceDao == null) {
            pieceDao = new JdbcPieceDao();
        }

        return pieceDao;
    }

    public JanggiService janggiService() {
        if (janggiService == null) {
            janggiService = new JanggiService(
                    gameDao(),
                    pieceDao(),
                    transactionExecutor()
            );
        }

        return janggiService;
    }

    public OutputView outputView() {
        if (outputView == null) {
            outputView = new OutputView();
        }

        return outputView;
    }

    public InputView inputView() {
        if (inputView == null) {
            inputView = new InputView();
        }

        return inputView;
    }

    public JanggiController janggiController() {
        if (janggiController == null) {
            janggiController = new JanggiController(
                    outputView(),
                    inputView(),
                    janggiService()
            );
        }

        return janggiController;
    }
}
