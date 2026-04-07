package repository;

import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.piece.Chariot;
import domain.piece.Elephant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PieceDaoTest {

    private static final String TEST_URL = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:schema.sql'";

    private GameDao gameDao;
    private PieceDao pieceDao;

    @BeforeEach
    void setUp() {
        DBConnection dbConnection = new H2DBConnection(TEST_URL);
        gameDao = new GameDao(dbConnection);
        pieceDao = new PieceDao(dbConnection);

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
    void 기물들을_저장한다() {
        long gameId = gameDao.save("CHO");

        Map<Position, Piece> piecesToSave = new HashMap<>();
        piecesToSave.put(new Position(0, 0), new Chariot(Team.CHO));
        piecesToSave.put(new Position(1, 0), new Elephant(Team.CHO));

        pieceDao.saveAll(gameId, piecesToSave);
        Map<Position, Piece> loadedPieces = pieceDao.findByGameId(gameId);

        assertThat(loadedPieces).hasSize(2);
    }

    @Test
    void 방의_기물들을_정확히_불러온다() {
        long gameId = gameDao.save("CHO");

        Map<Position, Piece> piecesToSave = new HashMap<>();
        piecesToSave.put(new Position(0, 0), new Chariot(Team.CHO));
        piecesToSave.put(new Position(1, 0), new Elephant(Team.CHO));

        pieceDao.saveAll(gameId, piecesToSave);
        Map<Position, Piece> loadedPieces = pieceDao.findByGameId(gameId);
        Piece chariot = loadedPieces.get(new Position(0, 0));

        assertThat(chariot.getPieceType()).isEqualTo(PieceType.CHARIOT);
        assertThat(chariot.getTeam()).isEqualTo(Team.CHO);
    }
}
