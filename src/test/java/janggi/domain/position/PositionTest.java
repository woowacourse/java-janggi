package janggi.domain.position;

import static janggi.domain.position.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("특정_위치에서_특정_방향에_있는_모든_위치_반환_테스트_케이스")
    public void 특정_위치에서_특정_방향에_있는_모든_위치를_반환한다(Direction direction, List<Position> results) {
        // given
        Position from = Position.from(5, 5);

        // when
        List<Position> positions = from.findPositionsByDirection(direction);

        // then
        assertThat(positions.getFirst()).isEqualTo(results.getFirst());
        assertThat(positions.getLast()).isEqualTo(results.getLast());
    }

    private static Stream<Arguments> 특정_위치에서_특정_방향에_있는_모든_위치_반환_테스트_케이스() {

        return Stream.of(
                Arguments.of(NORTH, List.of(
                        Position.from(4, 5), Position.from(1, 5))),
                Arguments.of(EAST, List.of(
                        Position.from(5, 6), Position.from(5, 9))),
                Arguments.of(SOUTH, List.of(
                        Position.from(6, 5), Position.from(10, 5))),
                Arguments.of(WEST, List.of(
                        Position.from(5, 4), Position.from(5, 1)))
        );
    }
}
