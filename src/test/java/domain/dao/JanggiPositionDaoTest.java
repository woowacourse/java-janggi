package domain.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import domain.position.JanggiPosition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestDatabaseConnector;

class JanggiPositionDaoTest {

    JanggiPositionDao positionDao = new JanggiPositionDao(new TestDatabaseConnector());

    @BeforeEach
    void initializeDatabase() {
        positionDao.deleteAll();
    }

    @Test
    void 장기_위치_생성_테스트() {
        // when & then
        assertDoesNotThrow(() -> positionDao.addPosition(new JanggiPosition(1, 2)));
    }

    @Test
    void 장기_위치_탐색_테스트() {
        // given
        JanggiPosition position = new JanggiPosition(1, 2);
        positionDao.addPosition(position);
        String positionId = positionDao.findByPosition(position);

        // when & then
        assertThat(positionDao.findPositionById(positionId))
                .isEqualTo(position);
    }

    @Test
    void 장기_위치가_없으면_생성한다() {
        // given
        JanggiPosition position = new JanggiPosition(1, 2);
        String positionId = positionDao.findByPosition(position);

        // when & then
        assertThat(positionDao.findPositionById(positionId))
                .isEqualTo(position);
    }
}
