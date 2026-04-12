package janggi.infra;

import janggi.dao.game.GameDao;
import janggi.dao.game.JdbcGameDao;
import janggi.dao.piece.JdbcPieceDao;
import janggi.dao.piece.PieceDao;
import janggi.infra.transaction.TransactionExecutor;
import janggi.infra.transaction.TransactionExecutorImpl;

public class PersistenceConfig {

    private final DbProperties dbProperties;
    private ConnectionProvider connectionProvider;
    private TransactionExecutor transactionExecutor;

    private GameDao gameDao;
    private PieceDao pieceDao;

    public PersistenceConfig(DbProperties dbProperties) {
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
}
