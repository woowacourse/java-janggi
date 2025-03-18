package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GeneralTest {

    @DisplayName("General이 위로 한 칸 움직일 경우, 행이 -1 되어야 한다.")
    @Test
    void when_general_move_then_column_minus_one() {
        General general = new General(new Position(1, 4), Team.RED);
        general.up();

        Position expectedPosition = new Position(0, 4);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("General이 아래로 한 칸 움직일 경우, 행이 +1 되어야 한다.")
    @Test
    void when_general_move_then_column_plus_one() {
        General general = new General(new Position(1, 4), Team.RED);
        general.down();

        Position expectedPosition = new Position(2, 4);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("General이 좌측으로 한 칸 움직일 경우, 열이 -1 되어야 한다.")
    @Test
    void when_general_move_then_row_minus_one() {
        General general = new General(new Position(1, 4), Team.RED);
        general.left();

        Position expectedPosition = new Position(1, 3);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("General이 우측으로 한 칸 움직일 경우, 열이 +1 되어야 한다.")
    @Test
    void when_general_move_then_row_plus_one() {
        General general = new General(new Position(1, 4), Team.RED);
        general.right();

        Position expectedPosition = new Position(1, 5);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @Test
    @DisplayName("General이 우측으로 두 칸 움직일 경우, 열이 +2 되어야 한다.")
    void when_general_move_then_row_plus_two() {
        General general = new General(new Position(1, 4), Team.RED);
        general.right();
        general.right();

        Position expectedPosition = new Position(1, 6);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @Test
    @DisplayName("General이 10행 9열을 벗어나면 예외가 발생한다.")
    void General이_10행_9열을_벗어나면_예외가_발생한다() {
        General general = new General(new Position(1, 4), Team.RED);
        general.up();

        assertThatThrownBy(() -> general.up())
                .isInstanceOf(IllegalArgumentException.class);
    }

}
