package janggi.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggi.domain.piece.direction.Position;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PositionDaoTest {

    private final PositionDao positionDao = new PositionDao();

    @BeforeAll
    static void setUpClass() {
        DatabaseConnection.setTestMode(true);
    }

    @BeforeEach
    void setUp() {
        positionDao.deleteAllPositions();
    }

    @AfterAll
    static void tearDownClass() {
        DatabaseConnection.setTestMode(false);
    }

    @DisplayName("데이터베이스에 포지션을 추가한다.")
    @Test
    void addPositionTest() {

        // given
        final Position position = new Position(1, 1);

        // when & then
        assertThatCode(() -> {
            positionDao.addPosition(position);
        }).doesNotThrowAnyException();
    }

    @DisplayName("positionId로 포지션 객체를 찾는다.")
    @Test
    void findIdByPositionTest() {

        // given
        final Position position = new Position(1, 1);

        // when
        positionDao.addPosition(position);
        int positionId = positionDao.findIdByPosition(position);
        Position findPosition = positionDao.findPositionById(positionId);

        // then
        assertThat(findPosition).isEqualTo(position);
    }

    @DisplayName("포지션을 삭제한다.")
    @Test
    void deletePositionTest() {

        // given
        final Position position = new Position(1, 1);
        positionDao.addPosition(position);

        // when
        positionDao.deletePosition(position);

        // then
        assertThatCode(() -> positionDao.findIdByPosition(position))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("오류가 발생했습니다.");
    }
}
