package janggi.domain.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Nested
    @DisplayName("위치는 움직임에 따라 이동할 수 있다.")
    class moveTest {

        @DisplayName("위로 한칸 움직일 수 있다.")
        @Test
        void Position_moveUp() {
            // given
            Position position = new Position(Row.TWO, Column.TWO);

            // when
            Position moved = position.move(Movement.from(Direction.UP));

            // then
            assertThat(moved).isEqualTo(new Position(Row.ONE, Column.TWO));
        }

        @DisplayName("아래로 한 칸 움직일 수 있다.")
        @Test
        void Position_moveDown() {
            // given
            Position position = new Position(Row.TWO, Column.TWO);

            // when
            Position moved = position.move(Movement.from(Direction.DOWN));

            // then
            assertThat(moved).isEqualTo(new Position(Row.THREE, Column.TWO));
        }

        @DisplayName("왼쪽으로 한 칸 움직일 수 있다.")
        @Test
        void Position_moveLeft() {
            // given
            Position position = new Position(Row.TWO, Column.TWO);

            // when
            Position moved = position.move(Movement.from(Direction.LEFT));

            // then
            assertThat(moved).isEqualTo(new Position(Row.TWO, Column.ONE));
        }

        @DisplayName("오른쪽으로 한 칸 움직일 수 있다.")
        @Test
        void Position_moveRight() {
            // given
            Position position = new Position(Row.TWO, Column.TWO);

            // when
            Position moved = position.move(Movement.from(Direction.RIGHT));

            // then
            assertThat(moved).isEqualTo(new Position(Row.TWO, Column.THREE));
        }
    }
}
