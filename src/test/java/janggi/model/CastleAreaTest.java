package janggi.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

class CastleAreaTest {

    @Test
    void 궁성의_특정_위치에서_갈_수_있는_방향을_계산한다() {
        Position position = new Position(9, 5);
        List<Direction> directions = CastleArea.calculateMovableDirections(position);

        assertThat(directions).containsExactlyInAnyOrder(
                Direction.BOTTOM,
                Direction.TOP,
                Direction.LEFT,
                Direction.RIGHT,
                Direction.LEFT_BOTTOM,
                Direction.LEFT_TOP,
                Direction.RIGHT_BOTTOM,
                Direction.RIGHT_TOP
        );
    }
}
