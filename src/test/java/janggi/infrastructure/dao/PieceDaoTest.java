package janggi.infrastructure.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.domain.Side;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceFactory;
import janggi.domain.space.Position;
import janggi.infrastructure.dao.dto.PieceEntity;
import janggi.infrastructure.db.DatabaseConnection;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceDaoTest {
    private static GameDao gameDao;
    private static PieceDao pieceDao;
    private Long gameId;

    @BeforeAll
    static void beforeAll() throws Exception {
        gameDao = new GameDao();
        pieceDao = new PieceDao();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             InputStream inputStream = PieceDaoTest.class.getClassLoader().getResourceAsStream("schema.sql")) {

            if (inputStream != null) {
                String schemaSql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                statement.execute(schemaSql);
            }
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM piece");
            statement.execute("DELETE FROM game");

            gameId = gameDao.insertGame(connection, "고래", "제이콥", "CHO");
        }
    }

    @Test
    void 특정_게임_ID에_속한_기물_목록을_DB에_일괄_저장_INSERT_한다() {
        // given
        Map<Position, Piece> board = Map.of(
                Position.of(0, 0), PieceFactory.createChariot(Side.CHO)
        );

        // when & then
        try (Connection connection = DatabaseConnection.getConnection()) {
            assertThatCode(() -> pieceDao.insertPieces(connection, gameId, board))
                    .doesNotThrowAnyException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 특정_게임_ID를_입력받아_DB에_저장된_해당_게임의_모든_기물_목록을_조회_SELECT_한다() throws Exception {
        // given
        Map<Position, Piece> board = Map.of(
                Position.of(0, 0), PieceFactory.createChariot(Side.CHO),
                Position.of(0, 1), PieceFactory.createElephant(Side.CHO)
        );

        try (Connection connection = DatabaseConnection.getConnection()) {
            pieceDao.insertPieces(connection, gameId, board);
        }

        // when
        List<PieceEntity> pieces = pieceDao.findAllByGameId(gameId);

        // then
        assertThat(pieces).hasSize(2);
        assertThat(pieces).extracting("pieceType")
                .containsExactlyInAnyOrder("CHARIOT", "ELEPHANT");
    }

    @Test
    void 특정_게임_ID를_입력받아_DB에_저장된_해당_게임의_모든_기물을_삭제_DELETE_한다() throws Exception {
        // given
        Map<Position, Piece> board = Map.of(
                Position.of(0, 0), PieceFactory.createChariot(Side.CHO)
        );

        try (Connection connection = DatabaseConnection.getConnection()) {
            pieceDao.insertPieces(connection, gameId, board);

            // when
            pieceDao.deleteAllByGameId(connection, gameId);
        }

        List<PieceEntity> pieces = pieceDao.findAllByGameId(gameId);

        // then
        assertThat(pieces).isEmpty();
    }
}
