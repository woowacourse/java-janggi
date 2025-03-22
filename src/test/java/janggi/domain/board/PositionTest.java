package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Nested
    @DisplayName("위치는 방향에 따라 움직일 수 있다.")
    class moveTest {

        @DisplayName("위로 움직일 수 있다.")
        @Test
        void Position_moveUp() {
            // given
            Position position = new Position(Row.TWO, Column.TWO);

            // when
            Position moved = position.move(Direction.UP);

            // then
            assertThat(moved).isEqualTo(new Position(Row.THREE, Column.TWO));
        }

        @DisplayName("아래로 움직일 수 있다.")
        @Test
        void Position_moveDown() {
            // given
            Position position = new Position(Row.TWO, Column.TWO);

            // when
            Position moved = position.move(Direction.DOWN);

            // then
            assertThat(moved).isEqualTo(new Position(Row.ONE, Column.TWO));
        }

        @DisplayName("왼쪽으로 움직일 수 있다.")
        @Test
        void Position_moveLeft() {
            // given
            Position position = new Position(Row.TWO, Column.TWO);

            // when
            Position moved = position.move(Direction.LEFT);

            // then
            assertThat(moved).isEqualTo(new Position(Row.TWO, Column.ONE));
        }

        @DisplayName("오른쪽으로 움직일 수 있다.")
        @Test
        void Position_moveRight() {
            // given
            Position position = new Position(Row.TWO, Column.TWO);

            // when
            Position moved = position.move(Direction.RIGHT);

            // then
            assertThat(moved).isEqualTo(new Position(Row.TWO, Column.THREE));
        }
    }
}
