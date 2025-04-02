package domain.dao;

import domain.Country;
import domain.dto.GameFindDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiGameDaoTest {
    JanggiGameDao gameDao = new JanggiGameDao(JanggiDBConnect.getConnection());

    @Nested
    class findTest {

        @Test
        void findAllGamesTest() {
            Assertions.assertDoesNotThrow(() -> gameDao.findGames(0, 10));
        }

        @Test
        void findGameIdByGameName() {
            int id = gameDao.createGame("roomName", Country.HAN);
            GameFindDto findDto = gameDao.getGameByName("roomName");
            gameDao.deleteGameRoom(id);

            assertThat(findDto.gameId()).isEqualTo(id);
        }
    }

    @Nested
    class deleteTest {

        @Test
        void deleteGameByNumberTest() {
            int id = gameDao.createGame("roomName", Country.HAN);
            Assertions.assertDoesNotThrow(() -> gameDao.deleteGameRoom(id));
        }
    }
}