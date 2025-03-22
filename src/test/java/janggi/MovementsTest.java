package janggi;

import static janggi.Movement.RIGHT;
import static janggi.Movement.UP;
import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import janggi.board.Position;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MovementsTest {
    @DisplayName("주어진_시작_지점부터_내부_움직임에_따라_경로를_만들고_반환한다")
    @Test
    void makePath() {
        // given
        Movements movements = new Movements(UP, UP, RIGHT);
        Position start = createPosition(3, 4);

        // when
        Path path = movements.makePath(start);

        // then
        assertThat(path).extracting("path", as(LIST))
                .containsExactly(start, start.up(), start.up().up(), start.up().up().right());
    }
}
