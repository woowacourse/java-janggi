package janggi;

import janggi.database.QueryProcessor;
import janggi.database.TestMySQLDatabaseConnection;
import janggi.database.dao.PieceDao;
import janggi.database.dao.TurnDao;
import janggi.repository.DefaultPieceRepository;
import janggi.repository.DefaultTurnRepository;
import janggi.repository.PieceRepository;
import janggi.repository.TurnRepository;

public class TestFixture {

    private static final QueryProcessor queryProcessor =
            new QueryProcessor(TestMySQLDatabaseConnection.getInstance());

    private static final PieceDao pieceDao = new PieceDao(queryProcessor);
    private static final TurnDao turnDao = new TurnDao(queryProcessor);

    private static final PieceRepository pieceRepository = new DefaultPieceRepository(pieceDao);
    private static final TurnRepository turnRepository = new DefaultTurnRepository(turnDao);

    public static PieceRepository getPieceRepository() {
        return pieceRepository;
    }

    public static TurnRepository getTurnRepository() {
        return turnRepository;
    }

    public static PieceDao getPieceDao() {
        return pieceDao;
    }

    public static TurnDao getTurnDao() {
        return turnDao;
    }
}
