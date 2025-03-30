package domain.game;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameDaoTest {

    private GameDao gameDao;

    @BeforeEach
    public void setUp() {
        gameDao = new GameDao();
    }

    @DisplayName("게임 저장 테스트")
    @Test
    void test1() {

        //given
        int gameId = 100;
        String status = "STARTED";

        //when
        Games games = gameDao.insertGame(gameId, status);
        // then
        Assertions.assertThat(games.getGameId()).isEqualTo(100);
        Assertions.assertThat(games.getGameStatus()).isEqualTo("STARTED");
        Assertions.assertThat(games.getBluePlayerId()).isEqualTo(-1);
        Assertions.assertThat(games.getRedPlayerId()).isEqualTo(-1);
        Assertions.assertThat(games.getCurrentTurn()).isEqualTo(0);
    }
}