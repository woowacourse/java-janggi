package janggi.dao.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dao.DatabaseTest;
import janggi.dao.game.GameEntity;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JdbcGameDaoTest extends DatabaseTest {

    @DisplayName("가장 마지막에 추가된 게임을 조회한다.")
    @Test
    void findLatestGame_success() {
        //given
        gameDao.save(con, "CHO");
        Long second = gameDao.save(con, "HAN");

        //when
        Optional<GameEntity> result = gameDao.findLatestGame(con);

        //then
        GameEntity found = result.get();

        assertThat(found.id())
                .isEqualTo(second);
    }

    @DisplayName("해당하는 게임이 존재하지 않으면 Optional.empty()를 반환한다.")
    @Test
    void findLatestGame_fail() {
        //when
        Optional<GameEntity> result = gameDao.findLatestGame(con);

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("게임을 삭제한다.")
    @Test
    void deleteByGameId() {
        //given
        Long gameId = gameDao.save(con, "HAN");

        //when
        gameDao.deleteByGameId(con, gameId);

        //then
        assertThat(gameDao.findLatestGame(con)).isEmpty();
    }

    @DisplayName("현재 턴 정보를 수정한다.")
    @Test
    void updateCurrentTurn() {
        //given
        Long gameId = gameDao.save(con, "HAN");

        //when
        gameDao.updateCurrentTurn(con, gameId, "CHO");

        //then
        Optional<GameEntity> latestGameOpt = gameDao.findLatestGame(con);
        GameEntity gameEntity = latestGameOpt.get();

        assertThat(gameEntity.currentTurn()).isEqualTo("CHO");
    }
}