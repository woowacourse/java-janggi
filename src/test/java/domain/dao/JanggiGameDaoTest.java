package domain.dao;

import domain.Country;
import domain.JanggiCoordinate;
import domain.PieceInitializer;
import domain.piece.Piece;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class JanggiGameDaoTest {
    JanggiGameDao gameDao = new JanggiGameDao(JanggiDBConnect.getConnection());

    @Nested
    class createTest {

        @Test
        void saveGameTest() {
            int num = gameDao.createGame("Test1", Country.HAN);
            Map<JanggiCoordinate, Piece> map = PieceInitializer.init();

            Assertions.assertDoesNotThrow(() -> gameDao.saveGame(num, map));

            gameDao.deleteGameRoom(num);
        }
    }

    @Nested
    class findTest {

        @Test
        void findAllGamesTest() {
            Assertions.assertDoesNotThrow(() -> gameDao.findGames(0, 10));
        }

        @Test
        void findGameIdByGameName() {
            int id = gameDao.createGame("roomName", Country.HAN);
            int findId = gameDao.getGameIdByName("roomName");
            gameDao.deleteGameRoom(id);

            assertThat(findId).isEqualTo(id);
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