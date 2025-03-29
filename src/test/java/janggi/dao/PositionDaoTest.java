package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.domain.piece.direction.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionDaoTest {

    @DisplayName("데이터베이스에 포지션을 추가한다.")
    @Test
    void addPositionTest() {

        // given
        final PositionDao positionDao = new PositionDao();

        // when
        final Position position = new Position(1, 1);

        // then
        assertThatCode(() -> {
            positionDao.addPosition(position);
            positionDao.deletePosition(position);
        }).doesNotThrowAnyException();
    }

    @DisplayName("데이터베이스에서 positionId로 찾는다.")
    @Test
    void findPositionByIdTest() {

        // given
        final PositionDao positionDao = new PositionDao();
        positionDao.addPosition(new Position(1, 1));
        final Position position = positionDao.findByPositionId(1);

        // when & then
        assertThat(position).isEqualTo(new Position(1, 1));
        positionDao.deletePosition(position);
    }

    @DisplayName("데이터베이스에서 좌표로 positionId를 찾는다.")
    @Test
    void findIdByXYTest() {

        // given
        final PositionDao positionDao = new PositionDao();
        final int x = 1;
        final int y = 1;

        // when & then
        positionDao.addPosition(new Position(x, y));
        int findPositionId = positionDao.findIdByXY(x, y);
        assertThat(findPositionId).isEqualTo(1);
        positionDao.deletePositionById(findPositionId);
    }
}
