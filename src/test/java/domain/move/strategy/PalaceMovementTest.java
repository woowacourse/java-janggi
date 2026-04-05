package domain.move.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.board.Intersection;
import domain.move.Path;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("궁성에서의 움직임 검증")
class PalaceMovementTest {

    private final PalaceMovement palaceMovementImplements = new PalaceMovement() {
    };

    @DisplayName("궁성의 귀(모서리, 코너) 부분에서만 궁성 중앙으로 이동할 수 있다")
    @Nested
    class 궁성_중앙_이동 {

        @DisplayName("궁성의 귀가 아닌 곳에서 이동을 시도하면 예외를 던진다")
        @Test
        void 궁성의_귀가_아닌_곳이면_예외를_던진다() {
            Intersection nonPalaceCornerIntersection = new Intersection(3, 5);
            assertThatThrownBy(() -> palaceMovementImplements.toPalaceCenter(nonPalaceCornerIntersection))
                    .isInstanceOf(IllegalStateException.class);
        }

        @DisplayName("궁성의 귀에서 이동을 시도하면 성공한다")
        @ParameterizedTest(name = "귀({0})에서 중앙으로 이동")
        @MethodSource("allPalaceCornerAndCenter")
        void 궁성의_귀면_성공(Intersection corner, Intersection expected) {
            Intersection palaceCenter = palaceMovementImplements.toPalaceCenter(corner);

            assertThat(palaceCenter).isEqualTo(expected);
        }

        private static Stream<Arguments> allPalaceCornerAndCenter() {
            final Intersection hanCenter = position(2, 5);
            final Intersection choCenter = position(9, 5);

            return Stream.of(
                    // HAN 진영
                    Arguments.of(position(1, 4), hanCenter),
                    Arguments.of(position(1, 6), hanCenter),
                    Arguments.of(position(3, 4), hanCenter),
                    Arguments.of(position(3, 6), hanCenter),
                    // CHO 진영
                    Arguments.of(position(8, 4), choCenter),
                    Arguments.of(position(8, 6), choCenter),
                    Arguments.of(position(10, 4), choCenter),
                    Arguments.of(position(10, 6), choCenter)
            );
        }
    }

    @DisplayName("연결된 대각선을 찾는다")
    @Nested
    class 연결된_대각선_리턴 {

        @DisplayName("궁성 내부가 아니면 빈 리스트를 리턴한다")
        @Test
        void 궁성_내부_아니면_빈_리스트_리턴() {
            List<Path> palaceDiagonalPaths = palaceMovementImplements.palaceDiagonalPaths(position(5, 5));

            assertThat(palaceDiagonalPaths)
                    .extracting(Path::destination)
                    .isEmpty();
        }

        @DisplayName("궁성 중앙인 경우, 네 개의 귀를 모두 리턴한다")
        @ParameterizedTest(name = "중앙({0})이 네 개의 귀({1})를 리턴한다")
        @MethodSource("centerAndCorners")
        void 궁성_중앙이면_네_개의_귀_리턴(Intersection center, List<Intersection> expected) {
            List<Path> palaceDiagonalPaths = palaceMovementImplements.palaceDiagonalPaths(center);

            assertThat(palaceDiagonalPaths)
                    .extracting(Path::destination)
                    .containsExactlyInAnyOrderElementsOf(expected);
        }

        @DisplayName("궁성 귀인 경우, 중앙을 리턴한다")
        @ParameterizedTest(name = "귀({0})가 중앙({1})을 리턴한다")
        @MethodSource("allPalaceCornerAndCenter")
        void 궁성_귀면_중앙_리턴(Intersection corner, Intersection expected) {
            List<Path> palaceDiagonalPaths = palaceMovementImplements.palaceDiagonalPaths(corner);

            assertThat(palaceDiagonalPaths)
                    .extracting(Path::destination)
                    .containsExactlyInAnyOrder(expected);
        }

        private static Stream<Arguments> centerAndCorners() {
            final Intersection hanCenter = position(2, 5);
            final Intersection choCenter = position(9, 5);

            return Stream.of(
                    Arguments.of(hanCenter, List.of(
                            position(1, 4), position(1, 6), position(3, 4), position(3, 6)
                    )),
                    Arguments.of(choCenter, List.of(
                            position(8, 4), position(8, 6), position(10, 4), position(10, 6)
                    ))
            );
        }

        private static Stream<Arguments> allPalaceCornerAndCenter() {
            final Intersection hanCenter = position(2, 5);
            final Intersection choCenter = position(9, 5);

            return Stream.of(
                    // HAN 진영
                    Arguments.of(position(1, 4), hanCenter),
                    Arguments.of(position(1, 6), hanCenter),
                    Arguments.of(position(3, 4), hanCenter),
                    Arguments.of(position(3, 6), hanCenter),
                    // CHO 진영
                    Arguments.of(position(8, 4), choCenter),
                    Arguments.of(position(8, 6), choCenter),
                    Arguments.of(position(10, 4), choCenter),
                    Arguments.of(position(10, 6), choCenter)
            );
        }
    }

    private static Intersection position(int row, int file) {
        return new Intersection(row, file);
    }
}
