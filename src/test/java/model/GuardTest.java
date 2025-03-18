package model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GuardTest {

    private Guard guard = new Guard(new Position(1, 4), Team.RED);

    @DisplayName("Guard가 위로 한 칸 움직일 경우, 행이 -1 되어야 한다.")
    @Test
    void when_general_move_then_column_minus_one() {
        guard.up();

        Position expectedPosition = new Position(0, 4);
        Position currentPosition = guard.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("Guard가 아래로 한 칸 움직일 경우, 행이 +1 되어야 한다.")
    @Test
    void when_general_move_then_column_plus_one() {
        guard.down();

        Position expectedPosition = new Position(2, 4);
        Position currentPosition = guard.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("Guard가 좌측으로 한 칸 움직일 경우, 열이 -1 되어야 한다.")
    @Test
    void when_general_move_then_row_minus_one() {
        guard.left();

        Position expectedPosition = new Position(1, 3);
        Position currentPosition = guard.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("Guard가 우측으로 한 칸 움직일 경우, 열이 +1 되어야 한다.")
    @Test
    void when_general_move_then_row_plus_one() {
        guard.right();

        Position expectedPosition = new Position(1, 5);
        Position currentPosition = guard.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @Test
    @DisplayName("Guard가 우측으로 두 칸 움직일 경우, 열이 +2 되어야 한다.")
    void when_general_move_then_row_plus_two() {
        guard.right();
        guard.right();

        Position expectedPosition = new Position(1, 6);
        Position currentPosition = guard.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @Test
    @DisplayName("Guard가 10행 9열을 벗어나면 예외가 발생한다.")
    void General이_10행_9열을_벗어나면_예외가_발생한다() {
        guard.up();

        assertThatThrownBy(() -> guard.up())
                .isInstanceOf(IllegalArgumentException.class);
    }

}
