package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("방향 테스트")
class DirectionTest {

    @DisplayName("위로 이동한다.")
    @Test
    void moveFromUp() {
        // given
        Point point = new Point(3, 3);
        Direction direction = Direction.UP;

        // when
        Point nextPoint = direction.moveFrom(point);

        // then
        assertThat(nextPoint)
                .isEqualTo(new Point(2, 3));
    }

    @DisplayName("아래로 이동한다.")
    @Test
    void moveFromDown() {
        // given
        Point point = new Point(3, 3);
        Direction direction = Direction.DOWN;

        // when
        Point nextPoint = direction.moveFrom(point);

        // then
        assertThat(nextPoint)
                .isEqualTo(new Point(4, 3));
    }

    @DisplayName("오른쪽으로 이동한다.")
    @Test
    void moveFromRight() {
        // given
        Point point = new Point(3, 3);
        Direction direction = Direction.RIGHT;

        // when
        Point nextPoint = direction.moveFrom(point);

        // then
        assertThat(nextPoint)
                .isEqualTo(new Point(3, 4));
    }

    @DisplayName("왼쪽으로 이동한다.")
    @Test
    void moveFromLeft() {
        // given
        Point point = new Point(3, 3);
        Direction direction = Direction.LEFT;

        // when
        Point nextPoint = direction.moveFrom(point);

        // then
        assertThat(nextPoint)
                .isEqualTo(new Point(3, 2));
    }

    @DisplayName("위 오른쪽 대각선으로 이동한다.")
    @Test
    void moveFromUpRight() {
        // given
        Point point = new Point(3, 3);
        Direction direction = Direction.UP_RIGHT;

        // when
        Point nextPoint = direction.moveFrom(point);

        // then
        assertThat(nextPoint)
                .isEqualTo(new Point(2, 4));
    }

    @DisplayName("위 왼쪽 대각선으로 이동한다.")
    @Test
    void moveFromUpLeft() {
        // given
        Point point = new Point(3, 3);
        Direction direction = Direction.UP_LEFT;

        // when
        Point nextPoint = direction.moveFrom(point);

        // then
        assertThat(nextPoint)
                .isEqualTo(new Point(2, 2));
    }

    @DisplayName("아래 오른쪽 대각선으로 이동한다.")
    @Test
    void moveFromDownRight() {
        // given
        Point point = new Point(3, 3);
        Direction direction = Direction.DOWN_RIGHT;

        // when
        Point nextPoint = direction.moveFrom(point);

        // then
        assertThat(nextPoint)
                .isEqualTo(new Point(4, 4));
    }

    @DisplayName("아래 왼쪽 대각선으로 이동한다.")
    @Test
    void moveFromDownLeft() {
        // given
        Point point = new Point(3, 3);
        Direction direction = Direction.DOWN_LEFT;

        // when
        Point nextPoint = direction.moveFrom(point);

        // then
        assertThat(nextPoint)
                .isEqualTo(new Point(4, 2));
    }

    @DisplayName("해당 방향이 보드 안이라면 이동할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "UP, 3, 3",
            "DOWN, 3, 3",
            "RIGHT, 3, 3",
            "LEFT, 3, 3",
            "UP_RIGHT, 3, 3",
            "UP_LEFT, 3, 3",
            "DOWN_RIGHT, 3, 3",
            "DOWN_LEFT, 3, 3"

    })
    void canMoveFrom(Direction direction, int x, int y) {
        // when, then
        assertThat(direction.canMoveFrom(new Point(x, y)))
                .isTrue();
    }

    @DisplayName("해당 방향이 보드 밖이라면 이동할 수 없다.")
    @ParameterizedTest
    @CsvSource({
            "UP, 1, 1",
            "DOWN, 10, 1",
            "RIGHT, 1, 9",
            "LEFT, 1, 1",
            "UP_RIGHT, 1, 9",
            "UP_LEFT, 1, 9",
            "DOWN_RIGHT, 10, 9",
            "DOWN_LEFT, 10, 9"

    })
    void canNotMoveFrom(Direction direction, int x, int y) {
        // when, then
        assertThat(direction.canMoveFrom(new Point(x, y)))
                .isFalse();
    }
}