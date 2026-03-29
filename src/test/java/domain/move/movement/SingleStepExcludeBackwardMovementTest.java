package domain.move.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import domain.move.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("좌, 우 전진 1칸 이동 테스트")
class SingleStepExcludeBackwardMovementTest {

    private SingleStepExcludeBackwardMovement movement;

    @BeforeEach
    void setUp() {
        movement = new SingleStepExcludeBackwardMovement();
    }

    @DisplayName("앞과 양 옆으로 1칸 이동이 가능하다")
    @Nested
    class 앞과_양_옆으로_1칸_이동이_가능하다 {

        private static final Intersection CURRENT_INTERSECTION = new Intersection(5, 5);

        @DisplayName("초 진영의 경우")
        @Test
        void 초_진영의_경우() {
            List<Path> movablePaths = movement.movablePaths(CURRENT_INTERSECTION, Side.CHO);
            List<Intersection> movableDestinations = movablePaths.stream()
                    .map(Path::destination)
                    .toList();

            assertThat(movableDestinations)
                    .containsExactlyInAnyOrder(
                            new Intersection(4, 5),
                            new Intersection(5, 4),
                            new Intersection(5, 6)
                    );
        }

        @DisplayName("한 진영의 경우")
        @Test
        void 한_진영의_경우() {
            List<Path> movablePaths = movement.movablePaths(CURRENT_INTERSECTION, Side.HAN);
            List<Intersection> movableDestinations = movablePaths.stream()
                    .map(Path::destination)
                    .toList();

            assertThat(movableDestinations)
                    .containsExactlyInAnyOrder(
                            new Intersection(6, 5),
                            new Intersection(5, 4),
                            new Intersection(5, 6)
                    );
        }
    }

    @DisplayName("범위 밖으로는 이동이 불가능하다")
    @Nested
    class 범위_밖으로는_이동이_불가능하다 {

        @DisplayName("초 진영의 경우")
        @ParameterizedTest(name = "경계점 검증: {0}")
        @MethodSource("allBorderIntersectionsForCho")
        void 초_진영의_경우(Intersection borderIntersection, List<Intersection> expected) {
            List<Path> movablePaths = movement.movablePaths(borderIntersection, Side.CHO);
            List<Intersection> movableDestinations = movablePaths.stream()
                    .map(Path::destination)
                    .toList();

            assertThat(movableDestinations)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        @DisplayName("한 진영의 경우")
        @ParameterizedTest(name = "경계점 검증: {0}")
        @MethodSource("allBorderIntersectionsForHan")
        void 한_진영의_경우(Intersection borderIntersection, List<Intersection> expected) {
            List<Path> movablePaths = movement.movablePaths(borderIntersection, Side.HAN);
            List<Intersection> movableDestinations = movablePaths.stream()
                    .map(Path::destination)
                    .toList();

            assertThat(movableDestinations)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        private static Stream<Arguments> allBorderIntersectionsForCho() {
            return Stream.of(
                    Arguments.of(new Intersection(1, 1),
                            List.of(new Intersection(1, 2))),
                    Arguments.of(new Intersection(1, 9),
                            List.of(new Intersection(1, 8))),
                    Arguments.of(new Intersection(10, 1),
                            List.of(new Intersection(9, 1), new Intersection(10, 2))),
                    Arguments.of(new Intersection(10, 9),
                            List.of(new Intersection(9, 9), new Intersection(10, 8)))
            );
        }

        private static Stream<Arguments> allBorderIntersectionsForHan() {
            return Stream.of(
                    Arguments.of(new Intersection(1, 1),
                            List.of(new Intersection(2, 1), new Intersection(1, 2))),
                    Arguments.of(new Intersection(1, 9),
                            List.of(new Intersection(2, 9), new Intersection(1, 8))),
                    Arguments.of(new Intersection(10, 1),
                            List.of(new Intersection(10, 2))),
                    Arguments.of(new Intersection(10, 9),
                            List.of(new Intersection(10, 8)))
            );
        }
    }
}
