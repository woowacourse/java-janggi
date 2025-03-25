package janggi.moving;

import static janggi.moving.Movement.RIGHT;
import static janggi.moving.Movement.UP;
import static janggi.fixture.PositionFixture.createPosition;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import janggi.board.position.Position;
import janggi.moving.Movement;
import janggi.moving.Movements;
import janggi.moving.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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

    @DisplayName("내부_움직임이_위를_향하는지_여부를_반환한다")
    @CsvSource(value = {"UP:true", "LEFT_UP:true", "RIGHT_UP:true", "DOWN:false"}, delimiterString = ":")
    @ParameterizedTest
    void isUpward(Movement movement, boolean expected) {
        // given
        Movements movements = new Movements(movement);

        // when
        boolean result = movements.isUpward();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("내부_움직임이_아래를_향하는지_여부를_반환한다")
    @CsvSource(value = {"DOWN:true", "LEFT_DOWN:true", "RIGHT_DOWN:true", "UP:false"}, delimiterString = ":")
    @ParameterizedTest
    void isDownward(Movement movement, boolean expected) {
        // given
        Movements movements = new Movements(movement);

        // when
        boolean result = movements.isDownward();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("내부_움직임이_왼쪽을_향하는지_여부를_반환한다")
    @CsvSource(value = {"LEFT:true", "LEFT_UP:true", "LEFT_DOWN:true", "UP:false"}, delimiterString = ":")
    @ParameterizedTest
    void isLeftward(Movement movement, boolean expected) {
        // given
        Movements movements = new Movements(movement);

        // when
        boolean result = movements.isLeftward();

        // then
        assertThat(result).isEqualTo(expected);
    }

    @DisplayName("내부_움직임이_오른쪽을_향하는지_여부를_반환한다")
    @CsvSource(value = {"RIGHT:true", "RIGHT_UP:true", "RIGHT_DOWN:true", "UP:false"}, delimiterString = ":")
    @ParameterizedTest
    void isRightward(Movement movement, boolean expected) {
        // given
        Movements movements = new Movements(movement);

        // when
        boolean result = movements.isRightward();

        // then
        assertThat(result).isEqualTo(expected);
    }
}
