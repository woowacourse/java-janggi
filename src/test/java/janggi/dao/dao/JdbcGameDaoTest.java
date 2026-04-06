package janggi.dao.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.dao.DatabaseTest;
import janggi.dao.game.GameEntity;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcGameDaoTest extends DatabaseTest {

    @DisplayName("게임을 삭제한다.")
    @Test
    void deleteByGameId() {
        //given
        Long gameId = gameDao.save(con, "HAN");

        //when
        gameDao.deleteByGameId(con, gameId);

        //then
        assertThatThrownBy(() -> gameDao.findByGameId(con, gameId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 게임이 존재하지 않습니다.");
    }

    @DisplayName("삭제할 게임이 없으면 예외가 발생한다.")
    @Test
    void deleteByGameId_empty() {
        assertThatThrownBy(() ->  gameDao.deleteByGameId(con, 100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 게임이 존재하지 않습니다.");
    }

    @DisplayName("현재 턴 정보를 수정한다.")
    @Test
    void updateCurrentTurn() {
        //given
        Long gameId = gameDao.save(con, "HAN");

        //when
        gameDao.updateCurrentTurn(con, gameId, "CHO");

        //then
        GameEntity gameEntity = gameDao.findByGameId(con, gameId);

        assertThat(gameEntity.currentTurn()).isEqualTo("CHO");
    }

    @DisplayName("해당 하는 게임이 없으면 턴을 업데이트할 수 없다.")
    @Test
    void updateCurrentTurn_empty() {
        assertThatThrownBy(() ->  gameDao.updateCurrentTurn(con, 100L, "CHO"))
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("해당 게임이 존재하지 않습니다.");
    }

    @DisplayName("game_id에 해당하는 게임을 조회한다.")
    @Test
    void findByGameId() {
        //given
        Long gameId = gameDao.save(con, "HAN");

        //when
        GameEntity gameEntity = gameDao.findByGameId(con, gameId);

        //then
        assertThat(gameEntity.currentTurn()).isEqualTo("HAN");
    }

    @DisplayName("game_id에 해당하는 게임이 없으면 예외가 발생한다.")
    @Test
    void findByGameId_empty() {
        //then
        assertThatThrownBy(() ->  gameDao.findByGameId(con, 1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 게임이 존재하지 않습니다.");
    }

    @DisplayName("모든 게임을 조회한다.")
    @Test
    void findAll() {
        //given
        Long gameId1 = gameDao.save(con, "HAN");
        Long gameId2 = gameDao.save(con, "CHO");
        Long gameId3 = gameDao.save(con, "HAN");

        //when
        List<GameEntity> gameEntities = gameDao.findAll(con);

        //then
        List<Long> gameIds = gameEntities.stream()
                .map(GameEntity::id)
                .toList();

        assertThat(gameIds).containsExactly(gameId1, gameId2, gameId3);

    }
}