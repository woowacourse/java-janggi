package service;


import java.sql.Connection;
import java.sql.SQLException;
import repository.dao.GameContextDao;
import repository.dao.GameDao;
import repository.dao.GamePieceDao;
import repository.dao.PieceDao;
import repository.jdbc.JdbcConnectionGenerator;

public class JanggiGameService {
    private final JdbcConnectionGenerator CONNECTION_GENERATOR;

    private final PieceDao pieceDao;
    private final GamePieceDao gamePieceDao;
    private final GameDao gameDao;
    private final GameContextDao gameContextDao;

    public JanggiGameService(JdbcConnectionGenerator connectionGenerator, PieceDao pieceDao, GamePieceDao gamePieceDao,
                             GameDao gameDao, GameContextDao gameContextDao) {
        CONNECTION_GENERATOR = connectionGenerator;
        this.pieceDao = pieceDao;
        this.gamePieceDao = gamePieceDao;
        this.gameDao = gameDao;
        this.gameContextDao = gameContextDao;
    }

    public void initializeDatabase() {
        try (Connection connection = CONNECTION_GENERATOR.getDBConnection()) {
            connection.setAutoCommit(false);
            try {
                pieceDao.initTable(connection);
                gamePieceDao.initTable(connection);
                gameDao.initTable(connection);
                gameContextDao.initTable(connection);
                connection.commit();
            } catch (Exception e) {
                connection.rollback();
                throw new RuntimeException("비즈니스 로직 예외로 인한 롤백 발생", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("데이터베이스 연결 오류", e);
        }
    }
}
