package janggi.board;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class PositionTest {

    @Test
    @DisplayName("좌표 이동 테스트")
    void test1() {
        Position position = new Position(1, 1);

        Position movedPosition = position.move(1, 1);

        assertThat(movedPosition).isEqualTo(new Position(2, 2));
    }

    @Test
    @DisplayName("좌표 이동 범위 테스트")
    void isOutOfRange() {

        int xLimit = 3;
        int yLimit = 3;

        Position position = new Position(0, 0);
        Position movedPosition1 = position.move(0, -1);
        Position movedPosition2 = position.move(3, 0);

        assertAll(
                () -> assertThat(position.isOutOfRange(xLimit, yLimit)).isFalse(),
                () -> assertThat(movedPosition1.isOutOfRange(xLimit, yLimit)).isTrue(),
                () -> assertThat(movedPosition2.isOutOfRange(xLimit, yLimit)).isTrue()
        );
    }

}