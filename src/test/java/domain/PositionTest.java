package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void 위치는_행과_열의_정보가_있어야한다() {
        Position position = new Position(0, 0);

        assertThat(position.getX()).isEqualTo(0);
        assertThat(position.getY()).isEqualTo(0);
    }

}
