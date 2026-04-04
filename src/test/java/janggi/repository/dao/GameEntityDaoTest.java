package janggi.repository.dao;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.repository.DatabaseTest;
import janggi.repository.entity.GameEntity;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameEntityDaoTest extends DatabaseTest {

    @DisplayName("가장 마지막에 추가된 게임을 조회한다.")
    @Test
    void findLatestGame_success() {
        //given
        gameEntityDao.save(con, "CHO");
        Long second = gameEntityDao.save(con, "HAN");

        //when
        Optional<GameEntity> result = gameEntityDao.findLatestGame(con);

        //then
        GameEntity found = result.get();

        assertThat(found.id())
                .isEqualTo(second);
    }

    @DisplayName("해당하는 게임이 존재하지 않으면 Optional.empty()를 반환한다.")
    @Test
    void findLatestGame_fail() {
        //when
        Optional<GameEntity> result = gameEntityDao.findLatestGame(con);

        //then
        assertThat(result).isEmpty();
    }

    @DisplayName("게임을 삭제한다.")
    @Test
    void deleteByGameId() {
        //given
        Long gameId = gameEntityDao.save(con, "HAN");

        //when
        gameEntityDao.deleteByGameId(con, gameId);

        //then
        assertThat(gameEntityDao.findLatestGame(con)).isEmpty();
    }
}