package janggi.infrastructure.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import janggi.infrastructure.dao.dto.PieceEntity;
import janggi.infrastructure.db.DatabaseConnection;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PieceDaoTest {
    private GameDao gameDao;
    private PieceDao pieceDao;
    private Long gameId;

    @BeforeEach
    void setUp() throws Exception {
        gameDao = new GameDao();
        pieceDao = new PieceDao();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             InputStream inputStream = getClass().getClassLoader().getResourceAsStream("schema.sql")) {

            assertNotNull(inputStream);
            String schemaSql = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            statement.execute(schemaSql);
        }

        gameId = gameDao.insertGame("고래", "제이콥", "CHO");
    }

    @Test
    void 특정_게임_ID에_속한_기물_목록을_DB에_일괄_저장_INSERT_한다() {
        // given
        int x = 0;
        int y = 0;
        String side = "CHO";
        String pieceType = "CHARIOT";

        // when & then
        assertThatCode(() -> pieceDao.insertPiece(gameId, x, y, side, pieceType))
                .doesNotThrowAnyException();
    }

    @Test
    void 특정_게임_ID를_입력받아_DB에_저장된_해당_게임의_모든_기물_목록을_조회_SELECT_한다() {
        // given
        pieceDao.insertPiece(gameId, 0, 0, "CHO", "CHARIOT");
        pieceDao.insertPiece(gameId, 0, 1, "CHO", "ELEPHANT");

        // when
        List<PieceEntity> pieces = pieceDao.findAllByGameId(gameId);

        // then
        assertThat(pieces).hasSize(2);
        assertThat(pieces).extracting("pieceType")
                .containsExactlyInAnyOrder("CHARIOT", "ELEPHANT");
    }


    @Test
    void 특정_게임_ID를_입력받아_DB에_저장된_해당_게임의_모든_기물을_삭제_DELETE_한다() {
        // given
        pieceDao.insertPiece(gameId, 0, 0, "CHO", "CHARIOT");

        // when
        pieceDao.deleteAllByGameId(gameId);
        List<PieceEntity> pieces = pieceDao.findAllByGameId(gameId);

        // then
        assertThat(pieces).isEmpty();
    }
}
