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

@DisplayName("모든 방향 1칸 이동 테스트")
class SingleStepMovementTest {

    private SingleStepMovement movement;

    @BeforeEach
    void setUp() {
        movement = new SingleStepMovement();
    }

    @DisplayName("앞, 뒤, 양 옆으로 1칸 이동이 가능하다")
    @Nested
    class 모든_빙향으로_1칸_이동이_가능하다 {

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
                            new Intersection(4, 5),
                            new Intersection(6, 5),
                            new Intersection(5, 4),
                            new Intersection(5, 6)
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
                            List.of(new Intersection(1, 2), new Intersection(2, 1))),
                    Arguments.of(new Intersection(1, 9),
                            List.of(new Intersection(1, 8), new Intersection(2, 9))),
                    Arguments.of(new Intersection(10, 1),
                            List.of(new Intersection(9, 1), new Intersection(10, 2))),
                    Arguments.of(new Intersection(10, 9),
                            List.of(new Intersection(9, 9), new Intersection(10, 8)))
            );
        }
    }

    @DisplayName("궁성 내에서는 위치에 따라 대각선 이동이 가능하다")
    @Nested
    class 궁성_내_대각선_이동 {

        @DisplayName("궁성 중심과 모서리에서는 대각선 이동 경로가 포함된다")
        @ParameterizedTest(name = "{0} 위치에서 {1} 진영의 이동 경로 검증")
        @MethodSource("palaceDiagonalData")
        void 궁성_내_대각선_이동_경로_생성_테스트(Intersection start, Side side, List<Intersection> expected) {
            // when
            List<Path> movablePaths = movement.movablePaths(start, side);
            List<Intersection> destinations = movablePaths.stream()
                    .map(Path::destination)
                    .toList();

            // then
            assertThat(destinations).containsExactlyInAnyOrderElementsOf(expected);
        }

        private static Stream<Arguments> palaceDiagonalData() {
            return Stream.of(
                    // 한나라(HAN) 궁성 중심 (2, 5)
                    Arguments.of(
                            new Intersection(2, 5),
                            Side.HAN,
                            List.of(
                                    new Intersection(1, 4), new Intersection(1, 5), new Intersection(1, 6),
                                    new Intersection(2, 4), new Intersection(2, 6),
                                    new Intersection(3, 4), new Intersection(3, 5), new Intersection(3, 6)
                            )
                    ),
                    // 한나라(HAN) 궁성 모서리 (1, 4)
                    Arguments.of(
                            new Intersection(1, 4),
                            Side.HAN,
                            List.of(
                                    new Intersection(1, 3), new Intersection(1, 5),
                                    new Intersection(2, 4), new Intersection(2, 5)
                            )
                    ),
                    // 초나라(CHO) 궁성 (9, 4)
                    Arguments.of(
                            new Intersection(9, 4),
                            Side.CHO,
                            List.of(
                                    new Intersection(8, 4),
                                    new Intersection(9, 3), new Intersection(9, 5),
                                    new Intersection(10, 4)
                            )
                    )
            );
        }
    }
}
