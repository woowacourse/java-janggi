package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PieceDaoTest {

    private static final String TEST_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";

    private GameDao gameDao;
    private PieceDao pieceDao;
    private DBConnection dbConnection;

    @BeforeEach
    void setUp() {
        dbConnection = new H2DBConnection(TEST_URL);
        gameDao = new GameDao();
        pieceDao = new PieceDao();

        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM piece");
            statement.execute("DELETE FROM game");
            statement.execute("ALTER TABLE game ALTER COLUMN id RESTART WITH 1");
        } catch (Exception e) {
            throw new RuntimeException("테스트 DB 초기화 실패", e);
        }
    }

    @Test
    void 기물들을_저장한다() throws SQLException {
        try (Connection connection = dbConnection.getConnection()) {
            long gameId = gameDao.create(connection, "CHO");

            List<PieceRow> piecesToSave = List.of(
                    new PieceRow(0, 0, "CHARIOT", "CHO"),
                    new PieceRow(1, 0, "ELEPHANT", "CHO")
            );

            pieceDao.saveAll(connection, gameId, piecesToSave);
            List<PieceRow> loadedPieces = pieceDao.findByGameId(connection, gameId);

            assertThat(loadedPieces).hasSize(2);
        }
    }

    @Test
    void 방의_기물들을_정확히_불러온다() throws SQLException {
        try (Connection connection = dbConnection.getConnection()) {
            long gameId = gameDao.create(connection, "CHO");

            List<PieceRow> piecesToSave = List.of(
                    new PieceRow(0, 0, "CHARIOT", "CHO"),
                    new PieceRow(1, 0, "ELEPHANT", "CHO")
            );

            pieceDao.saveAll(connection, gameId, piecesToSave);
            List<PieceRow> loadedPieces = pieceDao.findByGameId(connection, gameId);

            PieceRow chariot = loadedPieces.stream()
                    .filter(p -> p.x() == 0 && p.y() == 0)
                    .findFirst()
                    .orElseThrow();

            assertThat(chariot.pieceType()).isEqualTo("CHARIOT");
            assertThat(chariot.team()).isEqualTo("CHO");
        }
    }

    @Test
    void 위치로_기물_ID를_조회한다() throws SQLException {
        try (Connection connection = dbConnection.getConnection()) {
            long gameId = gameDao.create(connection, "CHO");
            pieceDao.saveAll(connection, gameId, List.of(new PieceRow(0, 0, "CHARIOT", "CHO")));

            Optional<Long> pieceId = pieceDao.findIdByPosition(connection, gameId, 0, 0);

            assertThat(pieceId).isPresent();
        }
    }

    @Test
    void 없는_위치를_조회하면_빈값을_반환한다() throws SQLException {
        try (Connection connection = dbConnection.getConnection()) {
            long gameId = gameDao.create(connection, "CHO");

            Optional<Long> pieceId = pieceDao.findIdByPosition(connection, gameId, 9, 9);

            assertThat(pieceId).isEmpty();
        }
    }

    @Test
    void 기물의_위치를_업데이트한다() throws SQLException {
        try (Connection connection = dbConnection.getConnection()) {
            long gameId = gameDao.create(connection, "CHO");
            pieceDao.saveAll(connection, gameId, List.of(new PieceRow(0, 0, "CHARIOT", "CHO")));

            long pieceId = pieceDao.findIdByPosition(connection, gameId, 0, 0).orElseThrow();
            pieceDao.updatePosition(connection, pieceId, 0, 5);

            Optional<Long> oldPosition = pieceDao.findIdByPosition(connection, gameId, 0, 0);
            Optional<Long> newPosition = pieceDao.findIdByPosition(connection, gameId, 0, 5);

            assertThat(oldPosition).isEmpty();
            assertThat(newPosition).isPresent();
        }
    }

    @Test
    void 기물을_ID로_삭제한다() throws SQLException {
        try (Connection connection = dbConnection.getConnection()) {
            long gameId = gameDao.create(connection, "CHO");
            pieceDao.saveAll(connection, gameId, List.of(new PieceRow(0, 0, "CHARIOT", "CHO")));

            long pieceId = pieceDao.findIdByPosition(connection, gameId, 0, 0).orElseThrow();
            pieceDao.deleteById(connection, pieceId);

            List<PieceRow> remaining = pieceDao.findByGameId(connection, gameId);
            assertThat(remaining).isEmpty();
        }
    }
}
