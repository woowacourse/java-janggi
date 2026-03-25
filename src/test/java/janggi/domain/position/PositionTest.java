package janggi.domain.position;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    public void 기존_포지션에_행과_열을_더해서_새로운_포지션을_만든다() {
        // given
        Position from = Position.from(1, 1);

        // when
        Position result = from.add(1, 1);

        // then
        assertThat(result).isEqualTo(Position.from(2, 2));
    }

}
