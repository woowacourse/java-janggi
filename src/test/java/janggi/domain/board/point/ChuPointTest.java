package janggi.domain.board.point;

import janggi.domain.board.Direction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ChuPointTest {

    @DisplayName("방향대로 움직일 수 있다")
    @ParameterizedTest
    @CsvSource({
            "UP, 2, 1, 1, 1",
            "DOWN, 2, 1, 3, 1",
            "RIGHT, 2, 2, 2, 3",
            "LEFT, 2, 2, 2, 1",
            "UP_RIGHT_DIAGONAL, 2, 2, 1, 3",
            "UP_LEFT_DIAGONAL, 2, 2, 1, 1",
            "DOWN_RIGHT_DIAGONAL, 2, 2, 3, 3",
            "DOWN_LEFT_DIAGONAL, 2, 2, 3, 1",
    })
    void move(Direction direction, int x1, int y1, int x2, int y2) {
        //given
        ChuPoint currentPoint = new ChuPoint(x1, y1);

        //when
        Point nextPoint = currentPoint.move(direction);

        //then
        Assertions.assertThat(nextPoint).isEqualTo(new ChuPoint(x2, y2));
    }
}