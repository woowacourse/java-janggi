package domain.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.janggiboard.customstrategy.InnerBoardArrangementStrategy;
import domain.janggiboard.customstrategy.LeftBoardArrangementStrategy;
import domain.janggiboard.customstrategy.OuterBoardArrangementStrategy;
import domain.janggiboard.customstrategy.RightBoardArrangementStrategy;
import domain.piece.JanggiSide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JanggiGameDaoTest {

    JanggiGameDao gameDao = new FakeJanggiGameDao();

    @BeforeEach
    void initializeGame() {
        gameDao.deleteAll();
    }

    @Test
    void 장기_게임_생성_테스트() {

        // when & then
        assertDoesNotThrow(() -> gameDao.addGame(new InnerBoardArrangementStrategy(JanggiSide.CHO), new OuterBoardArrangementStrategy(JanggiSide.HAN)));
    }

    @Test
    void 장기_게임_탐색_테스트() {
        // given
        gameDao.addGame(new LeftBoardArrangementStrategy(JanggiSide.CHO), new RightBoardArrangementStrategy(JanggiSide.HAN));
        int gameId = gameDao.getGame();

        // when & then
        assertAll(
                () -> assertThat(gameDao.findChoStrategyById(gameId))
                    .isInstanceOf(LeftBoardArrangementStrategy.class),
                () -> assertThat(gameDao.findHanStrategyById(gameId))
                    .isInstanceOf(RightBoardArrangementStrategy.class)
        );
    }

    @Test
    void 진행중인_장기_게임이_없을_수도_있다() {
        // when & then
        assertThat(gameDao.getGame()).isEqualTo(-1);
    }
}
