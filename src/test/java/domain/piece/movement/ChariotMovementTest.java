package domain.piece.movement;

import static org.assertj.core.api.Assertions.assertThat;

import domain.board.Intersection;
import domain.game.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("기물(차) 행마법 테스트")
class ChariotMovementTest {

    private ChariotMovement movement;

    @BeforeEach
    void setUp() {
        movement = new ChariotMovement();
    }

    @DisplayName("앞, 뒤, 양 옆으로 칸 수 제약없이 이동이 가능하다")
    @Nested
    class 모든_빙향으로_칸_수_제약없이_이동이_가능하다 {

        @DisplayName("진영에 상관없이 동일하게 적용된다")
        @ParameterizedTest
        @EnumSource(Side.class)
        void 진영에_상관없이_동일하게_적용된다(Side side) {
            Intersection currentIntersection = new Intersection(5, 5);
            List<Intersection> expected = allDirectionIntersectionsExcludeCurrentIntersection(currentIntersection);

            List<Path> movablePaths = movement.movablePaths(currentIntersection, side);
            List<Intersection> movableDestinations = movablePaths.stream()
                    .map(Path::destination)
                    .toList();

            assertThat(movableDestinations)
                    .containsExactlyInAnyOrderElementsOf(expected);
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
                            allDirectionIntersectionsExcludeCurrentIntersection(new Intersection(1, 1))),
                    Arguments.of(new Intersection(1, 9),
                            allDirectionIntersectionsExcludeCurrentIntersection(new Intersection(1, 9))),
                    Arguments.of(new Intersection(10, 1),
                            allDirectionIntersectionsExcludeCurrentIntersection(new Intersection(10, 1))),
                    Arguments.of(new Intersection(10, 9),
                            allDirectionIntersectionsExcludeCurrentIntersection(new Intersection(10, 9)))
            );
        }
    }

    private static List<Intersection> allDirectionIntersectionsExcludeCurrentIntersection(
            Intersection currentIntersection
    ) {
        int currentRow = currentIntersection.row();
        int currentFile = currentIntersection.file();

        List<Intersection> expected = new ArrayList<>();

        IntStream.rangeClosed(1, 10)
                .filter(row -> row != currentRow)
                .mapToObj(row -> new Intersection(row, currentFile))
                .forEach(expected::add);

        IntStream.rangeClosed(1, 9)
                .filter(file -> file != currentFile)
                .mapToObj(file -> new Intersection(currentRow, file))
                .forEach(expected::add);

        return expected;
    }
}
