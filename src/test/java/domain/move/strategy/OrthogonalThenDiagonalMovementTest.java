package domain.move.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import domain.move.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("날일자 이동 테스트")
class OrthogonalThenDiagonalMovementTest {

    private Movement movement;

    @BeforeEach
    void setUp() {
        movement = new OrthogonalThenDiagonalMovement();
    }

    @DisplayName("앞, 뒤, 양 옆 방향마다 두 갈래의 날일 방향으로 이동이 가능하다")
    @Nested
    class 모든_방향마다_두_갈래의_날일_방향으로_이동이_가능하다 {

        @DisplayName("진영에 상관없이 동일하게 적용된다")
        @ParameterizedTest
        @EnumSource(value = Side.class, names = "NONE", mode = EnumSource.Mode.EXCLUDE)
        void 진영에_상관없이_동일하게_적용된다(Side side) {
            Intersection currentIntersection = new Intersection(5, 5);

            List<Path> movablePaths = movement.movablePaths(currentIntersection, side);
            List<Intersection> movableDestinations = movablePaths.stream()
                    .map(Path::destination)
                    .toList();

            assertThat(movableDestinations)
                    .containsExactlyInAnyOrder(
                            new Intersection(3, 4),
                            new Intersection(3, 6),
                            new Intersection(7, 4),
                            new Intersection(7, 6),
                            new Intersection(4, 3),
                            new Intersection(6, 3),
                            new Intersection(4, 7),
                            new Intersection(6, 7)
                    );
        }
    }

    @DisplayName("범위 밖으로는 이동이 불가능하다")
    @Nested
    class 범위_밖으로는_이동이_불가능하다 {

        @DisplayName("진영에 상관없이 동일하게 적용된다")
        @ParameterizedTest(name = "경계점 검증: {0}")
        @MethodSource("allBorderIntersections")
        void 진영에_상관없이_동일하게_적용된다(Intersection borderIntersection, List<Intersection> expected) {
            List<Path> movablePathsCho = movement.movablePaths(borderIntersection, Side.CHO);
            List<Path> movablePathsHan = movement.movablePaths(borderIntersection, Side.HAN);
            List<Intersection> movableDestinationsCho = movablePathsCho.stream()
                    .map(Path::destination)
                    .toList();
            List<Intersection> movableDestinationsHan = movablePathsHan.stream()
                    .map(Path::destination)
                    .toList();

            assertThat(movableDestinationsCho)
                    .containsExactlyInAnyOrderElementsOf(expected);
            assertThat(movableDestinationsHan)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        private static Stream<Arguments> allBorderIntersections() {
            return Stream.of(
                    Arguments.of(new Intersection(1, 1),
                            List.of(new Intersection(2, 3), new Intersection(3, 2))),
                    Arguments.of(new Intersection(1, 9),
                            List.of(new Intersection(2, 7), new Intersection(3, 8))),
                    Arguments.of(new Intersection(10, 1),
                            List.of(new Intersection(8, 2), new Intersection(9, 3))),
                    Arguments.of(new Intersection(10, 9),
                            List.of(new Intersection(8, 8), new Intersection(9, 7)))
            );
        }
    }
}
