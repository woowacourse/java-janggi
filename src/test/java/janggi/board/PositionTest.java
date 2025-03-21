package janggi.board;

import janggi.move.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PositionTest {

    @Test
    @DisplayName("좌표 이동 테스트")
    void test1() {
        Position position = new Position(1, 1);

        Position movedPosition = position.move(Direction.RIGHT);

        assertThat(movedPosition).isEqualTo(new Position(2, 1));
    }

    @Test
    @DisplayName("좌표 이동 범위 테스트")
    void isOutOfRange() {

        int xLimit = 3;
        int yLimit = 3;

        Position position = new Position(0, 0);
        Position movedPosition1 = position.move(Direction.UP);

        assertAll(
                () -> assertThat(position.isOutOfRange(xLimit, yLimit)).isFalse(),
                () -> assertThat(movedPosition1.isOutOfRange(xLimit, yLimit)).isTrue()
        );
    }

}