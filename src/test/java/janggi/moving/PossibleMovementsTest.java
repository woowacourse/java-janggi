package janggi.moving;

import static janggi.fixture.PositionFixture.createPosition;
import static janggi.moving.Movement.DOWN;
import static janggi.moving.Movement.LEFT;
import static janggi.moving.Movement.LEFT_DOWN;
import static janggi.moving.Movement.LEFT_UP;
import static janggi.moving.Movement.RIGHT;
import static janggi.moving.Movement.RIGHT_DOWN;
import static janggi.moving.Movement.RIGHT_UP;
import static janggi.moving.Movement.UP;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import janggi.board.position.Position;
import java.util.List;
import org.junit.jupiter.api.Test;

class PossibleMovementsTest {
    @Test
    void aa() {
        // given
        PossibleMovements possibleMovements = new PossibleMovements(
                List.of(new Movements(UP, RIGHT_UP, RIGHT_UP), new Movements(RIGHT, RIGHT_UP, RIGHT_UP),
                        new Movements(UP, LEFT_UP, LEFT_UP), new Movements(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
                        new Movements(DOWN, RIGHT_DOWN, RIGHT_DOWN), new Movements(DOWN, LEFT_DOWN, LEFT_DOWN),
                        new Movements(LEFT, LEFT_UP, LEFT_UP), new Movements(LEFT, LEFT_DOWN, LEFT_DOWN)));

        Position start = createPosition(1, 1);
        Position goal = createPosition(3, 4);

        // when
        Path result = possibleMovements.calculatePath(start, goal);

        // then
        assertThat(result).extracting("path", as(LIST))
                .containsExactly(start, start.up(), start.up().rightUp(), start.up().rightUp().rightUp());
    }

    @Test
    void aaa() {
        // given
        PossibleMovements possibleMovements = new PossibleMovements(
                List.of(new Movements(UP, RIGHT_UP, RIGHT_UP), new Movements(RIGHT, RIGHT_UP, RIGHT_UP)));

        Position start = createPosition(1, 1);
        Position goal = createPosition(3, 7);

        // when
        // then
        assertThatThrownBy(() -> possibleMovements.calculatePath(start, goal))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 선택하신 기물은 해당 목적지로 이동할 수 없습니다.");
    }
}
