package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class JolTest {

    private Jol jol = new Jol(new Position(1, 4));

    @DisplayName("Jol이 위로 한 칸 움직일 경우, 행이 -1 되어야 한다.")
    @Test
    void when_jol_move_then_column_minus_one() {
        jol.up(1);

        Position expectedPosition = new Position(0, 4);
        Position currentPosition = jol.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("Jol이 아래로 움직이려고 할 경우 예외가 발생한다.")
    @Test
    void when_jol_move_then_throw_exception() {
        assertThatThrownBy(() -> jol.down(1))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("해당 기물이 이동할 수 없는 방향입니다.");
    }

    @DisplayName("Jol이 좌측으로 한 칸 움직일 경우, 열이 -1 되어야 한다.")
    @Test
    void when_jol_move_then_row_minus_one() {
        jol.left(1);

        Position expectedPosition = new Position(1, 3);
        Position currentPosition = jol.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("Jol이 우측으로 한 칸 움직일 경우, 열이 +1 되어야 한다.")
    @Test
    void when_jol_move_then_row_plus_one() {
        jol.right(1);

        Position expectedPosition = new Position(1, 5);
        Position currentPosition = jol.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @Test
    @DisplayName("Jol이 우측으로 두 칸 움직일 경우, 열이 +2 되어야 한다.")
    void when_jol_move_then_row_plus_two() {
        jol.right(1);
        jol.right(1);

        Position expectedPosition = new Position(1, 6);
        Position currentPosition = jol.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @Test
    @DisplayName("Jol이 10행 9열을 벗어나면 예외가 발생한다.")
    void Jol이_10행_9열을_벗어나면_예외가_발생한다() {
        jol.up(1);

        assertThatThrownBy(() -> jol.up(1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    @DisplayName("Jol이 두 칸 이동하려고 하는 경우, 예외를 발생해야 한다.")
    class JolCanMoveOnlyOne {

        @Test
        @DisplayName("왼쪽으로 두 칸 이동하는 경우, 예외를 발생시켜야 한다.")
        void when_jol_move_left_amount_over_one_then_throw_exception(){
            assertThatThrownBy(() -> jol.left(2))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("오른쪽으로 두 칸 이동하는 경우, 예외를 발생시켜야 한다.")
        void when_jol_move_right_amount_over_one_then_throw_exception(){
            assertThatThrownBy(() -> jol.right(2))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("위로 두 칸 이동하는 경우, 예외를 발생시켜야 한다.")
        void when_jol_move_up_amount_over_one_then_throw_exception(){
            assertThatThrownBy(() -> jol.up(2))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

}
