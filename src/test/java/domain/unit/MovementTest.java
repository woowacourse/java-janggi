package domain.unit;

import static org.assertj.core.api.Assertions.assertThat;

import domain.movement.Direction;
import domain.movement.Movement;
import domain.position.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovementTest {
    @Test
    @DisplayName("생성할 수 있는 경로인지 확인한다")
    void test1() {
        // given
        Movement lowerOnce = Movement.of(Direction.LOWER);
        Movement lowerTwice = Movement.of(Direction.LOWER, Direction.LOWER);
        Position position = Position.of(0, 1);

        // when
        boolean canBe = lowerOnce.canBeRoute(position);
        boolean canNotBe = lowerTwice.canBeRoute(position);

        // then
        assertThat(canBe).isTrue();
        assertThat(canNotBe).isFalse();
    }
}
