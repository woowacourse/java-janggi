package janggi.db.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.db.DbConnector;
import janggi.db.entity.GameEntity;
import janggi.db.entity.PieceEntity;
import janggi.domain.common.Team;
import janggi.domain.piece.PieceType;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PieceDaoTest {
    private GameDao gameDao;
    private PieceDao pieceDao;

    @BeforeEach
    void 테스트_시작() {
        DbConnector.initDatabase();
        gameDao = new GameDao();
        pieceDao = new PieceDao();
    }

    @AfterEach
    void 테스트_종료() {
        try (Connection connection = DbConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE pieces");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("기물의 정보를 한꺼번에 저장할 수 있다")
    void 기물의_정보_한꺼번에_저장() {
        // given
        GameEntity game = new GameEntity(null, Team.CHO, false);
        Long savedId = gameDao.save(game);
        PieceEntity piece1 = new PieceEntity(savedId, 1, 10, PieceType.CHA, Team.CHO);
        PieceEntity piece2 = new PieceEntity(savedId, 2, 10, PieceType.MA, Team.CHO);
        PieceEntity piece3 = new PieceEntity(savedId, 1, 1, PieceType.CHA, Team.HAN);
        PieceEntity piece4 = new PieceEntity(savedId, 2, 1, PieceType.MA, Team.HAN);

        // when
        pieceDao.saveAll(List.of(piece1, piece2, piece3, piece4));

        // then
        assertThat(pieceDao.findAllByGameId(savedId).size()).isEqualTo(4);
    }

    @Test
    @DisplayName("기물의 정보를 한꺼번에 삭제할 수 있다")
    void 기물의_정보_한꺼번에_삭제() {
        // given
        GameEntity game = new GameEntity(null, Team.CHO, false);
        Long savedId = gameDao.save(game);
        PieceEntity piece1 = new PieceEntity(savedId, 1, 10, PieceType.CHA, Team.CHO);
        PieceEntity piece2 = new PieceEntity(savedId, 2, 10, PieceType.MA, Team.CHO);
        PieceEntity piece3 = new PieceEntity(savedId, 1, 1, PieceType.CHA, Team.HAN);
        PieceEntity piece4 = new PieceEntity(savedId, 2, 1, PieceType.MA, Team.HAN);
        pieceDao.saveAll(List.of(piece1, piece2, piece3, piece4));

        // when
        pieceDao.deleteAllByGameId(savedId);

        // then
        assertThat(pieceDao.findAllByGameId(savedId).size()).isEqualTo(0);
    }

    @Test
    @DisplayName("기물의 정보를 한꺼번에 조회할 수 있다")
    void 기물의_정보_한꺼번에_조회() {
        // given
        GameEntity game = new GameEntity(null, Team.CHO, false);
        Long savedId = gameDao.save(game);
        PieceEntity piece1 = new PieceEntity(savedId, 1, 10, PieceType.CHA, Team.CHO);
        PieceEntity piece2 = new PieceEntity(savedId, 2, 10, PieceType.MA, Team.CHO);
        PieceEntity piece3 = new PieceEntity(savedId, 1, 1, PieceType.CHA, Team.HAN);
        PieceEntity piece4 = new PieceEntity(savedId, 2, 1, PieceType.MA, Team.HAN);
        pieceDao.saveAll(List.of(piece1, piece2, piece3, piece4));

        // when
        List<PieceEntity> result = pieceDao.findAllByGameId(savedId);

        // then
        assertThat(result.size()).isEqualTo(4);
    }
}
