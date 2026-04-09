package repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

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
    void 기물들을_저장한다() throws Exception {
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
    void 방의_기물들을_정확히_불러온다() throws Exception {
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
}
