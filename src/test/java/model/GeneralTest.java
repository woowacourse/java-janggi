package model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GeneralTest {

    @DisplayName("General이 위로 한 칸 움직일 경우, 행이 -1 되어야 한다.")
    @Test
    void when_general_move_then_column_minus_one() {
        Position position = new Position(1, 4);
        General general = new General(position);
        general.up();

        Position expectedPosition = new Position(0, 4);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("General이 아래로 한 칸 움직일 경우, 행이 +1 되어야 한다.")
    @Test
    void when_general_move_then_column_plus_one() {
        Position position = new Position(1, 4);
        General general = new General(position);
        general.down();

        Position expectedPosition = new Position(2, 4);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("General이 좌측으로 한 칸 움직일 경우, 열이 -1 되어야 한다.")
    @Test
    void when_general_move_then_row_minus_one() {
        Position position = new Position(1, 4);
        General general = new General(position);
        general.left();

        Position expectedPosition = new Position(1, 3);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

    @DisplayName("General이 우측으로 한 칸 움직일 경우, 열이 +1 되어야 한다.")
    @Test
    void when_general_move_then_row_plus_one() {
        Position position = new Position(1, 4);
        General general = new General(position);
        general.right();

        Position expectedPosition = new Position(1, 5);
        Position currentPosition = general.getPosition();
        assertThat(expectedPosition).isEqualTo(currentPosition);
    }

}
