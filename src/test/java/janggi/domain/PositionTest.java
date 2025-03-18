package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@ReplaceUnderBar
public class PositionTest {

    @Test
    void 가로_세로_2차원_좌표를_가진다() {
        Position position = new Position(1, 2);
        assertThat(position.getX()).isEqualTo(1);
        assertThat(position.getY()).isEqualTo(2);
    }

    @Test
    void 좌표를_변경할_수_있다() {
        Position position = new Position(1, 2);

        Position moved = position.moveTo(2, 4);
        assertThat(moved.getX()).isEqualTo(2);
        assertThat(moved.getY()).isEqualTo(4);
    }
}
