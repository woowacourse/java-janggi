package janggi.domain.position;

import static janggi.domain.position.Direction.EAST;
import static janggi.domain.position.Direction.NORTH;
import static janggi.domain.position.Direction.NORTHEAST;
import static janggi.domain.position.Direction.NORTHWEST;
import static janggi.domain.position.Direction.SOUTH;
import static janggi.domain.position.Direction.SOUTHEAST;
import static janggi.domain.position.Direction.SOUTHWEST;
import static janggi.domain.position.Direction.WEST;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PositionTest {

    @ParameterizedTest
    @MethodSource("특정_위치에서_특정_방향에_있는_모든_위치_반환_테스트_케이스")
    public void 특정_위치에서_특정_방향에_있는_모든_위치를_반환한다(Direction direction, List<Position> results) {
        // given
        Position from = Position.from(5, 5);

        // when
        List<Position> positions = from.findAllPositionsByDirection(direction);

        // then
        assertThat(positions.getFirst()).isEqualTo(results.getFirst());
        assertThat(positions.getLast()).isEqualTo(results.getLast());
    }

    @ParameterizedTest
    @MethodSource("특정_위치에서_특정_방향에_있는_위치_하나_반환_테스트_케이스")
    public void 특정_위치에서_특정_방향에_있는_위치_하나를_반환한다(Direction direction, Position result) {
        // given
        Position from = Position.from(5, 5);

        // when
        Optional<Position> to = from.nextPositionByDirection(direction);

        // then
        assertThat(to).isPresent().get().isEqualTo(result);
    }

    @ParameterizedTest(name = "({0},{1})에서는 대각선 이동이 가능하다.")
    @MethodSource("현재_위치에서_대각선_방향을_포함하여_이동가능한_방향을_확인하는_테스트_케이스")
    void 현재_위치에서_대각선_방향을_포함하여_이동가능한_방향을_확인한다(int row, int column, List<Direction> directions) {
        // when
        Position from = Position.from(row, column);
        List<Direction> directionsFrom = from.directions();

        // then
        assertThat(directionsFrom).containsExactlyInAnyOrderElementsOf(directions);
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

    private static Stream<Arguments> 특정_위치에서_특정_방향에_있는_위치_하나_반환_테스트_케이스() {
        return Stream.of(
                Arguments.of(NORTH, Position.from(4, 5)),
                Arguments.of(NORTHEAST, Position.from(4, 6)),
                Arguments.of(EAST, Position.from(5, 6)),
                Arguments.of(SOUTHEAST, Position.from(6, 6)),
                Arguments.of(SOUTH, Position.from(6, 5)),
                Arguments.of(SOUTHWEST, Position.from(6, 4)),
                Arguments.of(WEST, Position.from(5, 4)),
                Arguments.of(NORTHWEST, Position.from(4, 4))
        );
    }

    private static Stream<Arguments> 현재_위치에서_대각선_방향을_포함하여_이동가능한_방향을_확인하는_테스트_케이스() {
        return Stream.of(
                Arguments.of(1, 4, List.of(NORTH, EAST, SOUTH, WEST, SOUTHEAST)),
                Arguments.of(1, 6, List.of(NORTH, EAST, SOUTH, WEST, SOUTHWEST)),
                Arguments.of(2, 5, List.of(NORTH, EAST, SOUTH, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST)),
                Arguments.of(3, 4, List.of(NORTH, EAST, SOUTH, WEST, NORTHEAST)),
                Arguments.of(3, 6, List.of(NORTH, EAST, SOUTH, WEST, NORTHWEST)),
                Arguments.of(8, 4, List.of(NORTH, EAST, SOUTH, WEST, SOUTHEAST)),
                Arguments.of(8, 6, List.of(NORTH, EAST, SOUTH, WEST, SOUTHWEST)),
                Arguments.of(9, 5, List.of(NORTH, EAST, SOUTH, WEST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST)),
                Arguments.of(10, 4, List.of(NORTH, EAST, SOUTH, WEST, NORTHEAST)),
                Arguments.of(10, 6, List.of(NORTH, EAST, SOUTH, WEST, NORTHWEST))
        );
    }

}
