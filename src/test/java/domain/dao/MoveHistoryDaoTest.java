package domain.dao;

import domain.dto.HistoryDto;
import domain.janggiboard.customstrategy.InnerBoardArrangementStrategy;
import domain.piece.JanggiSide;
import domain.position.JanggiPosition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MoveHistoryDaoTest {

    final MoveHistoryDao historyDao = new FakeMoveHistoryDao();
    final JanggiGameDao gameDao = new FakeJanggiGameDao();
    final JanggiPositionDao positionDao = new FakeJanggiPositionDao();

    @BeforeEach
    void initializeDatabase() {
        historyDao.deleteAll();
        gameDao.deleteAll();
        positionDao.deleteAll();
        ;
    }

    @Test
    void 장기_기록_생성_테스트() {
        // given
        gameDao.addGame(new InnerBoardArrangementStrategy(JanggiSide.CHO), new InnerBoardArrangementStrategy(JanggiSide.HAN));
        int gameId = gameDao.getGame();

        JanggiPosition origin = new JanggiPosition(1, 2);
        positionDao.addPosition(origin);
        int originId = positionDao.findByPosition(origin);

        JanggiPosition destination = new JanggiPosition(2, 3);
        positionDao.addPosition(destination);
        int destinationId = positionDao.findByPosition(destination);

        // when & then
        assertDoesNotThrow(() -> historyDao.addHistory(gameId, originId, destinationId));
    }

    @Test
    void 장기_기록_검색_테스트() {
        // given
        gameDao.addGame(new InnerBoardArrangementStrategy(JanggiSide.CHO), new InnerBoardArrangementStrategy(JanggiSide.HAN));
        int gameId = gameDao.getGame();

        JanggiPosition origin1 = new JanggiPosition(1, 2);
        positionDao.addPosition(origin1);
        int origin1Id = positionDao.findByPosition(origin1);

        JanggiPosition destination1 = new JanggiPosition(2, 3);
        positionDao.addPosition(destination1);
        int destination1Id = positionDao.findByPosition(destination1);

        JanggiPosition origin2 = new JanggiPosition(3, 3);
        positionDao.addPosition(origin2);
        int origin2Id = positionDao.findByPosition(origin2);

        JanggiPosition destination2 = new JanggiPosition(5, 6);
        positionDao.addPosition(destination2);
        int destination2Id = positionDao.findByPosition(destination2);

        // when
        historyDao.addHistory(gameId, origin1Id, destination1Id);
        historyDao.addHistory(gameId, origin2Id, destination2Id);

        // then
        Assertions.assertThat(historyDao.getAllHistory(gameId))
                .containsExactly(new HistoryDto(origin1Id, destination1Id), new HistoryDto(origin2Id, destination2Id));
    }
}
