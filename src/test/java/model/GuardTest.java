package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GuardTest {

    private Guard guard = new Guard(new Position(1, 4), Team.RED);

    @DisplayName("Guard가 위로 한 칸 움직일 경우, 행이 -1 되어야 한다.")
    @Test
    void when_guard_move_then_column_minus_one() {
        guard.up(1);

        Position expectedPosition = new Position(0, 4);
        Position currentPosition = guard.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("Guard가 아래로 한 칸 움직일 경우, 행이 +1 되어야 한다.")
    @Test
    void when_guard_move_then_column_plus_one() {
        guard.down(1);

        Position expectedPosition = new Position(2, 4);
        Position currentPosition = guard.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("Guard가 좌측으로 한 칸 움직일 경우, 열이 -1 되어야 한다.")
    @Test
    void when_guard_move_then_row_minus_one() {
        guard.left(1);

        Position expectedPosition = new Position(1, 3);
        Position currentPosition = guard.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("Guard가 우측으로 한 칸 움직일 경우, 열이 +1 되어야 한다.")
    @Test
    void when_guard_move_then_row_plus_one() {
        guard.right(1);

        Position expectedPosition = new Position(1, 5);
        Position currentPosition = guard.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @Test
    @DisplayName("Guard가 우측으로 두 칸 움직일 경우, 열이 +2 되어야 한다.")
    void when_guard_move_then_row_plus_two() {
        guard.right(1);
        guard.right(1);

        Position expectedPosition = new Position(1, 6);
        Position currentPosition = guard.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @Test
    @DisplayName("Guard가 10행 9열을 벗어나면 예외가 발생한다.")
    void Guard가_10행_9열을_벗어나면_예외가_발생한다() {
        guard.up(1);

        assertThatThrownBy(() -> guard.up(1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Nested
    @DisplayName("왕이 두 칸 이동하려고 하는 경우, 예외를 발생해야 한다.")
    class GeneralCanMoveOnlyOne {

        @Test
        @DisplayName("왼쪽으로 두 칸 이동하는 경우, 예외를 발생시켜야 한다.")
        void when_guard_move_left_amount_over_one_then_throw_exception(){
            assertThatThrownBy(() -> guard.left(2))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("오른쪽으로 두 칸 이동하는 경우, 예외를 발생시켜야 한다.")
        void when_guard_move_right_amount_over_one_then_throw_exception(){
            assertThatThrownBy(() -> guard.right(2))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("아래로 두 칸 이동하는 경우, 예외를 발생시켜야 한다.")
        void when_guard_move_down_amount_over_one_then_throw_exception(){
            assertThatThrownBy(() -> guard.down(2))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        @DisplayName("아래로 두 칸 이동하는 경우, 예외를 발생시켜야 한다.")
        void when_guard_move_up_amount_over_one_then_throw_exception(){
            assertThatThrownBy(() -> guard.up(2))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
