package domain.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.janggiboard.customstrategy.LeftBoardArrangementStrategy;
import domain.janggiboard.customstrategy.RightBoardArrangementStrategy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestDatabaseConnector;

class JanggiGameDaoTest {

    JanggiGameDao gameDao = new JanggiGameDao(new TestDatabaseConnector());

    @BeforeEach
    void initializeGame() {
        gameDao.deleteAll();
    }

    @Test
    void 장기_게임_생성_테스트() {

        // when & then
        assertDoesNotThrow(() -> gameDao.addGame(1, 2));
    }

    @Test
    void 장기_게임_탐색_테스트() {
        // given
        gameDao.addGame(1, 2);
        String gameId = gameDao.getGame();

        // when & then
        assertAll(
                () -> assertThat(gameDao.findChoStrategyById(gameId))
                    .isInstanceOf(LeftBoardArrangementStrategy.class),
                () -> assertThat(gameDao.findHanStrategyById(gameId))
                    .isInstanceOf(RightBoardArrangementStrategy.class)
        );

    }
}
