package piece.position;

import move.direction.Direction;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PositionTest {
    @Test
    void 포지션은_숫자를_가진다() {

        Assertions.assertThatNoException().isThrownBy(() -> new Position(0, 0));
    }

    @Test
    void 포지션은_방향을_더할_수_있다() {
        Position position = new Position(0, 0);
        Direction direction = Direction.UP;

        Position newPosition = position.add(direction);

        Assertions.assertThat(newPosition).isEqualTo(new Position(1, 0));
    }
}
