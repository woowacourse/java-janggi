package janggi.dao.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.dao.DatabaseTest;
import janggi.dao.game.GameEntity;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcGameDaoTest extends DatabaseTest {

    @DisplayName("게임을 삭제한다.")
    @Test
    void deleteGameByGameId() {
        //given
        Long gameId = gameDao.saveGame(connection, "HAN");

        //when
        gameDao.deleteGameByGameId(connection, gameId);

        //then
        assertThat(gameDao.findGameByGameId(connection, gameId))
                .isEmpty();
    }

    @DisplayName("삭제할 게임이 없으면 예외가 발생한다.")
    @Test
    void deleteGameByGameId_empty() {
        assertThatThrownBy(() -> gameDao.deleteGameByGameId(connection, 100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 게임이 존재하지 않습니다.");
    }

    @DisplayName("현재 턴 정보를 수정한다.")
    @Test
    void updateCurrentTurn() {
        //given
        Long gameId = gameDao.saveGame(connection, "HAN");

        //when
        gameDao.updateCurrentTurn(connection, gameId, "CHO");

        //then
        GameEntity gameEntity = gameDao.findGameByGameId(connection, gameId).get();

        assertThat(gameEntity.currentTurn()).isEqualTo("CHO");
    }

    @DisplayName("해당 하는 게임이 없으면 턴을 업데이트할 수 없다.")
    @Test
    void updateCurrentTurn_empty() {
        assertThatThrownBy(() -> gameDao.updateCurrentTurn(connection, 100L, "CHO"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 게임이 존재하지 않습니다.");
    }

    @DisplayName("game_id에 해당하는 게임을 조회한다.")
    @Test
    void findGameByGameId() {
        //given
        Long gameId = gameDao.saveGame(connection, "HAN");

        //when
        GameEntity gameEntity = gameDao.findGameByGameId(connection, gameId).get();

        //then
        assertThat(gameEntity.currentTurn()).isEqualTo("HAN");
    }

    @DisplayName("game_id에 해당하는 게임이 없으면 Optional.empty()를 반환한다.")
    @Test
    void findGameByGameId_empty() {
        assertThat(gameDao.findGameByGameId(connection, 1L))
                .isEmpty();
    }

    @DisplayName("모든 게임을 조회한다.")
    @Test
    void findAllGames() {
        //given
        Long gameId1 = gameDao.saveGame(connection, "HAN");
        Long gameId2 = gameDao.saveGame(connection, "CHO");
        Long gameId3 = gameDao.saveGame(connection, "HAN");

        //when
        List<GameEntity> gameEntities = gameDao.findAllGames(connection);

        //then
        List<Long> gameIds = gameEntities.stream()
                .map(GameEntity::id)
                .toList();

        assertThat(gameIds).containsExactly(gameId1, gameId2, gameId3);

    }
}