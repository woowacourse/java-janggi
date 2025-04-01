package janggi.repository;

import janggi.database.QueryProcessor;
import janggi.database.TestMySQLDatabaseConnection;
import janggi.database.dao.PieceDao;
import janggi.database.dao.TurnDao;

public class TestFixture {

    private static final QueryProcessor queryProcessor =
            new QueryProcessor(TestMySQLDatabaseConnection.getInstance());

    private static final PieceDao pieceDao = new PieceDao(queryProcessor);
    private static final TurnDao turnDao = new TurnDao(queryProcessor);

    private static final PieceRepository pieceRepository = new JdbcPieceRepository(pieceDao);
    private static final TurnRepository turnRepository = new JdbcTurnRepository(turnDao);

    public static PieceRepository getPieceRepository() {
        return pieceRepository;
    }

    public static TurnRepository getTurnRepository() {
        return turnRepository;
    }
}
