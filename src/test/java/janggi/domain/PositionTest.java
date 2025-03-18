package janggi.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@ReplaceUnderBar
public class PositionTest {

    @Test
    void 가로_세로_2차원_좌표를_가진다() {
        Position position = new Position(1, 2);
        assertThat(position.getX()).isEqualTo(1);
        assertThat(position.getY()).isEqualTo(2);
    }
}
