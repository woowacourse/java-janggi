package domain.position;

import domain.direction.Direction;
import domain.direction.Vector;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class PalaceTest {

    @ParameterizedTest
    @MethodSource("positionAndResult")
    void 궁성에서_추가로_이동할_수_있는_방향을_반환한다(final Position start, final Set<Direction> expected) {
        // when
        Set<Direction> result = Palace.getMovableDirectionInPalace(start);

        // then
        Assertions.assertThat(result).containsAll(expected);
    }

    private static Stream<Arguments> positionAndResult() {
        return Stream.of(
                Arguments.of(Position.of(4, 1), Set.of(new Direction(List.of(Vector.DOWN_RIGHT)))),
                Arguments.of(Position.of(4, 3), Set.of(new Direction(List.of(Vector.UP_RIGHT)))),
                Arguments.of(Position.of(6, 1), Set.of(new Direction(List.of(Vector.DOWN_LEFT)))),
                Arguments.of(Position.of(6, 3), Set.of(new Direction(List.of(Vector.UP_LEFT)))),
                Arguments.of(Position.of(5, 2),
                        Set.of(
                                new Direction(List.of(Vector.UP_LEFT)),
                                new Direction(List.of(Vector.UP_RIGHT)),
                                new Direction(List.of(Vector.DOWN_LEFT)),
                                new Direction(List.of(Vector.DOWN_RIGHT))
                        )),
                Arguments.of(Position.of(4, 8), Set.of(new Direction(List.of(Vector.DOWN_RIGHT)))),
                Arguments.of(Position.of(4, 10), Set.of(new Direction(List.of(Vector.UP_RIGHT)))),
                Arguments.of(Position.of(6, 8), Set.of(new Direction(List.of(Vector.DOWN_LEFT)))),
                Arguments.of(Position.of(6, 10), Set.of(new Direction(List.of(Vector.UP_LEFT)))),
                Arguments.of(Position.of(5, 9),
                        Set.of(
                                new Direction(List.of(Vector.UP_LEFT)),
                                new Direction(List.of(Vector.UP_RIGHT)),
                                new Direction(List.of(Vector.DOWN_LEFT)),
                                new Direction(List.of(Vector.DOWN_RIGHT))
                        ))
        );
    }
}
