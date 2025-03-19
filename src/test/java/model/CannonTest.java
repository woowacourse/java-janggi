package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CannonTest {
    
    private Piece cannon = new Cannon(new Position(5, 5), Team.RED);

    @DisplayName("cannon이 위로 세 칸 움직일 경우, 행이 -3 되어야 한다.")
    @Test
    void when_cannon_move_then_column_minus_one() {
        cannon.up(3);

        Position expectedPosition = new Position(2, 5);
        Position currentPosition = cannon.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("cannon이 아래로 세 칸 움직일 경우, 행이 +3 되어야 한다.")
    @Test
    void when_cannon_move_then_column_plus_one() {
        cannon.down(3);

        Position expectedPosition = new Position(8, 5);
        Position currentPosition = cannon.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("cannon이 좌측으로 세 칸 움직일 경우, 열이 -3 되어야 한다.")
    @Test
    void when_cannon_move_then_row_minus_one() {
        cannon.left(3);

        Position expectedPosition = new Position(5, 2);
        Position currentPosition = cannon.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("cannon이 우측으로 세 칸 움직일 경우, 열이 +3 되어야 한다.")
    @Test
    void when_cannon_move_then_row_plus_one() {
        cannon.right(3);

        Position expectedPosition = new Position(5, 8);
        Position currentPosition = cannon.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("Cannon이 10행 9열을 벗어나면 예외가 발생한다")
    @Nested
    class CannonMoveException {

        @DisplayName("up인 경우")
        @Test
        void when_up() {
            assertThatThrownBy(() -> cannon.up(6))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("down인 경우")
        @Test
        void when_down() {
            assertThatThrownBy(() -> cannon.down(6))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("left인 경우")
        @Test
        void when_left() {
            assertThatThrownBy(() -> cannon.left(6))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("right인 경우")
        @Test
        void when_right() {
            assertThatThrownBy(() -> cannon.right(6))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("1만큼 움직이면 예외를 발생시켜야 한다")
    @Nested
    class MoveOneException {


        @DisplayName("up인 경우")
        @Test
        void when_up() {
            assertThatThrownBy(() -> cannon.up(1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("down인 경우")
        @Test
        void when_down() {
            assertThatThrownBy(() -> cannon.down(1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("left인 경우")
        @Test
        void when_left() {
            assertThatThrownBy(() -> cannon.left(1))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @DisplayName("right인 경우")
        @Test
        void when_right() {
            assertThatThrownBy(() -> cannon.right(1))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
