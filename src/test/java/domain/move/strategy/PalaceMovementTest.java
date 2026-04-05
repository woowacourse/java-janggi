package domain.move.strategy;

import static org.assertj.core.api.Assertions.assertThat;

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

        @DisplayName("궁성 귀에서의 이동")
        @Nested
        class 궁성_귀_이동 {

            @DisplayName("1칸을 이동할 수 있다면 중앙을 리턴한다")
            @ParameterizedTest(name = "귀({0})가 중앙({1})을 리턴한다")
            @MethodSource("allPalaceCornerAndCenter")
            void 한칸_이동이면_중앙_리턴(Intersection corner, Intersection expected) {
                List<Path> palaceDiagonalPaths = palaceMovementImplements.palaceDiagonalPaths(corner);

                assertThat(palaceDiagonalPaths)
                        .extracting(Path::destination)
                        .containsExactlyInAnyOrder(expected);
            }

            @DisplayName("칸 수 제한없이 이동할 수 있다면 중앙 또는 그 너머의 귀로 이동할 수 있다")
            @ParameterizedTest(name = "귀({0}에서 갈 수 있는 경로: {1}")
            @MethodSource("allPalaceCornerAndPaths")
            void 칸_수_제한_없으면_중앙을_경로로_하고_그_너머의_귀를_목적지로_리턴(Intersection corner, List<Path> expected) {
                List<Path> palaceDiagonalPaths = palaceMovementImplements.palaceDiagonalPathsForStraight(corner);

                assertThat(palaceDiagonalPaths).containsExactlyInAnyOrderElementsOf(expected);
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

            private static Stream<Arguments> allPalaceCornerAndPaths() {
                final Intersection hanCenter = position(2, 5);
                final Intersection choCenter = position(9, 5);

                return Stream.of(
                        // HAN 진영 Path1=중앙을 거쳐 반대편 귀, Path2=중앙
                        Arguments.of(position(1, 4),
                                List.of(
                                        new Path(position(3, 6), List.of(hanCenter)),
                                        Path.of(hanCenter)
                                )
                        ),
                        Arguments.of(position(1, 6),
                                List.of(
                                        new Path(position(3, 4), List.of(hanCenter)),
                                        Path.of(hanCenter)
                                )
                        ),
                        Arguments.of(position(3, 4),
                                List.of(
                                        new Path(position(1, 6), List.of(hanCenter)),
                                        Path.of(hanCenter)
                                )
                        ),
                        Arguments.of(position(3, 6),
                                List.of(
                                        new Path(position(1, 4), List.of(hanCenter)),
                                        Path.of(hanCenter)
                                )
                        ),
                        // CHO 진영 Path1=중앙을 거쳐 반대편 귀, Path2=중앙
                        Arguments.of(position(8, 4),
                                List.of(
                                        new Path(position(10, 6), List.of(choCenter)),
                                        Path.of(choCenter)
                                )
                        ),
                        Arguments.of(position(8, 6),
                                List.of(
                                        new Path(position(10, 4), List.of(choCenter)),
                                        Path.of(choCenter)
                                )
                        ),
                        Arguments.of(position(10, 4),
                                List.of(
                                        new Path(position(8, 6), List.of(choCenter)),
                                        Path.of(choCenter)
                                )
                        ),
                        Arguments.of(position(10, 6),
                                List.of(
                                        new Path(position(8, 4), List.of(choCenter)),
                                        Path.of(choCenter)
                                )
                        )
                );
            }
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
    }

    private static Intersection position(int row, int file) {
        return new Intersection(row, file);
    }
}
