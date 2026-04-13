package janggi.db.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.db.DbConnector;
import janggi.db.entity.GameEntity;
import janggi.domain.common.Team;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameDaoTest {
    private GameDao gameDao;

    @BeforeEach
    void 테스트_시작() {
        DbConnector.initDatabase();
        gameDao = new GameDao();
    }

    @AfterEach
    void 테스트_종료() {
        try (Connection connection = DbConnector.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE pieces");
            statement.execute("SET REFERENTIAL_INTEGRITY FALSE");
            statement.execute("TRUNCATE TABLE games");
            statement.execute("SET REFERENTIAL_INTEGRITY TRUE");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("새로 시작하는 게임의 현재 차례와 게임 진행 여부를 저장할 수 있다")
    void 새로운_게임_저장() {
        // given
        GameEntity game = new GameEntity(null, Team.CHO, false);

        // when
        Long savedId = gameDao.save(game);

        // then
        assertThat(savedId).isNotNull();
        Optional<GameEntity> result = gameDao.findById(savedId);
        assertThat(result.get().getTurn()).isEqualTo(Team.CHO);
        assertThat(result.get().isFinished()).isFalse();
    }

    @Test
    @DisplayName("이미 진행중이던 게임의 현재 차례와 게임 진행 여부를 업데이트할 수 있다")
    void 진행중인_게임_업데이트() {
        // given
        GameEntity game = new GameEntity(null, Team.HAN, false);
        Long savedId = gameDao.save(game);

        GameEntity updatedGame = new GameEntity(savedId, Team.CHO, false);

        // when
        gameDao.update(updatedGame);

        // then
        Optional<GameEntity> result = gameDao.findById(savedId);
        assertThat(result.get().getTurn()).isEqualTo(Team.CHO);
        assertThat(result.get().isFinished()).isFalse();
    }

    @Test
    @DisplayName("진행 중인 게임을 최대 5개 가져올 수 있다")
    void 진행중인_게임_가져오기() {
        // given
        GameEntity game1 = new GameEntity(null, Team.HAN, false);
        Long savedId1 = gameDao.save(game1);
        GameEntity game2 = new GameEntity(null, Team.CHO, false);
        Long savedId2 = gameDao.save(game2);
        GameEntity game3 = new GameEntity(null, Team.CHO, false);
        Long savedId3 = gameDao.save(game3);
        GameEntity game4 = new GameEntity(null, Team.HAN, false);
        Long savedId4 = gameDao.save(game4);
        GameEntity game5 = new GameEntity(null, Team.CHO, false);
        Long savedId5 = gameDao.save(game5);
        GameEntity game6 = new GameEntity(null, Team.HAN, false);
        Long savedId6 = gameDao.save(game6);

        // when
        List<GameEntity> games = gameDao.findOngoingGames();

        // then
        assertThat(games.size()).isEqualTo(5);
    }

    @Test
    @DisplayName("진행 중인 게임을 최대 5개 가져올 수 있다")
    void 진행중인_게임_가져오기_5개_미만() {
        // given
        GameEntity game1 = new GameEntity(null, Team.HAN, true);
        Long savedId1 = gameDao.save(game1);
        GameEntity game2 = new GameEntity(null, Team.CHO, false);
        Long savedId2 = gameDao.save(game2);
        GameEntity game3 = new GameEntity(null, Team.CHO, false);
        Long savedId3 = gameDao.save(game3);
        GameEntity game4 = new GameEntity(null, Team.HAN, true);
        Long savedId4 = gameDao.save(game4);
        GameEntity game5 = new GameEntity(null, Team.CHO, false);
        Long savedId5 = gameDao.save(game5);
        GameEntity game6 = new GameEntity(null, Team.HAN, false);
        Long savedId6 = gameDao.save(game6);

        // when
        List<GameEntity> games = gameDao.findOngoingGames();

        // then
        assertThat(games.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("게임 ID를 통해 게임을 불러올 수 있다")
    void 게임_ID로_게임_조회() {
        // given
        GameEntity game = new GameEntity(null, Team.HAN, false);
        Long savedId = gameDao.save(game);
        GameEntity updateGame = new GameEntity(savedId, Team.CHO, false);
        gameDao.update(updateGame);

        // when
        Optional<GameEntity> result = gameDao.findById(savedId);

        // then
        assertThat(result.get().getTurn()).isEqualTo(Team.CHO);
        assertThat(result.get().isFinished()).isFalse();
    }
}
