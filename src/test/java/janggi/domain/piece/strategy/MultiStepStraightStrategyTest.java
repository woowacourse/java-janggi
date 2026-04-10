package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Position;
import janggi.domain.piece.movement.strategy.MoveStrategy;
import janggi.domain.piece.movement.strategy.MultiStepStraightStrategy;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MultiStepStraightStrategyTest {

    private final MoveStrategy strategy = new MultiStepStraightStrategy();

    @DisplayName("정상 경우")
    @Nested
    class success {
        private static Stream<Arguments> createPositionsAndPath() {
            return Stream.of(
                    Arguments.of(new Position(0, 0), new Position(9, 0), 9,
                            List.of(
                                    new Position(1, 0),
                                    new Position(2, 0),
                                    new Position(3, 0),
                                    new Position(4, 0),
                                    new Position(5, 0),
                                    new Position(6, 0),
                                    new Position(7, 0),
                                    new Position(8, 0),
                                    new Position(9, 0)
                            )),
                    Arguments.of(new Position(0, 0), new Position(0, 8), 8,
                            List.of(
                                    new Position(0, 1),
                                    new Position(0, 2),
                                    new Position(0, 3),
                                    new Position(0, 4),
                                    new Position(0, 5),
                                    new Position(0, 6),
                                    new Position(0, 7),
                                    new Position(0, 8)
                            )
                    ),
                    Arguments.of(new Position(0, 8), new Position(0, 0), 8,
                            List.of(
                                    new Position(0, 7),
                                    new Position(0, 6),
                                    new Position(0, 5),
                                    new Position(0, 4),
                                    new Position(0, 3),
                                    new Position(0, 2),
                                    new Position(0, 1),
                                    new Position(0, 0)
                            )
                    ),
                    Arguments.of(new Position(9, 0), new Position(0, 0), 9,
                            List.of(
                                    new Position(8, 0),
                                    new Position(7, 0),
                                    new Position(6, 0),
                                    new Position(5, 0),
                                    new Position(4, 0),
                                    new Position(3, 0),
                                    new Position(2, 0),
                                    new Position(1, 0),
                                    new Position(0, 0)
                            )
                    ),
                    Arguments.of(new Position(0, 5), new Position(4, 5), 4,
                            List.of(
                                    new Position(1, 5),
                                    new Position(2, 5),
                                    new Position(3, 5),
                                    new Position(4, 5)
                            )
                    )
            );
        }

        @ParameterizedTest
        @MethodSource("createPositionsAndPath")
        void 차와_포는_한_방향으로만_1칸_이상_이동_할_수_있다(Position source, Position destination, int size,
                                           List<Position> expectedPath) {
            List<Position> path = strategy.findPath(source, destination);

            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(size);
                assertSoftly.assertThat(path).containsExactlyElementsOf(expectedPath);
            });
        }

        private static Stream<Arguments> createDiagonalPositionsAndPath() {
            return Stream.of(
                    Arguments.of(new Position(0, 3), new Position(2, 5), 2,
                            List.of(
                                    new Position(1, 4),
                                    new Position(2, 5)
                            )),
                    Arguments.of(new Position(0, 5), new Position(2, 3), 2,
                            List.of(
                                    new Position(1, 4),
                                    new Position(2, 3)
                            )),
                    Arguments.of(new Position(7, 3), new Position(9, 5), 2,
                            List.of(
                                    new Position(8, 4),
                                    new Position(9, 5)
                            )),
                    Arguments.of(new Position(7, 5), new Position(9, 3), 2,
                            List.of(
                                    new Position(8, 4),
                                    new Position(9, 3)
                            )),
                    Arguments.of(new Position(1, 4), new Position(2, 5), 1,
                            List.of(
                                    new Position(2, 5)
                            ))
            );
        }

        @ParameterizedTest
        @MethodSource("createDiagonalPositionsAndPath")
        void 차와_포는_궁성_내에서_대각선으로_이동_할_수_있다(Position source, Position destination, int size,
                                          List<Position> expectedPath) {
            List<Position> path = strategy.findPath(source, destination);

            SoftAssertions.assertSoftly(assertSoftly -> {
                assertSoftly.assertThat(path).hasSize(size);
                assertSoftly.assertThat(path).containsExactlyElementsOf(expectedPath);
            });
        }
    }

    @DisplayName("예외 경우")
    @Nested
    class exception {
        private static Stream<Arguments> exceptionInvalidPalaceDiagonalPath() {
            return Stream.of(
                    Arguments.of(new Position(0, 4), new Position(1, 3)),
                    Arguments.of(new Position(0, 4), new Position(1, 5)),
                    Arguments.of(new Position(1, 3), new Position(2, 4)),
                    Arguments.of(new Position(1, 5), new Position(2, 4)),
                    Arguments.of(new Position(7, 4), new Position(8, 3)),
                    Arguments.of(new Position(7, 4), new Position(8, 5)),
                    Arguments.of(new Position(8, 3), new Position(9, 4)),
                    Arguments.of(new Position(8, 5), new Position(9, 4))
            );
        }

        @ParameterizedTest
        @MethodSource("exceptionInvalidPalaceDiagonalPath")
        void 차와_포는_궁성_대각선이_연결되지_않은_경로로_이동할_수_없다(Position source, Position destination) {
            assertThatThrownBy(() -> strategy.findPath(source, destination))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 궁성 내에 대각선이 존재하지 않는 경로 입니다.");
        }

        private static Stream<Arguments> exceptionDiagonalPathOutOfPalace() {
            return Stream.of(
                    Arguments.of(new Position(0, 3), new Position(3, 6)),
                    Arguments.of(new Position(3, 6), new Position(0, 3)),
                    Arguments.of(new Position(7, 3), new Position(6, 2)),
                    Arguments.of(new Position(6, 2), new Position(7, 3))
            );
        }

        @ParameterizedTest
        @MethodSource("exceptionDiagonalPathOutOfPalace")
        void 차와_포는_궁성_영역을_벗어나는_대각선으로_이동할_수_없다(Position source, Position destination) {
            assertThatThrownBy(() -> strategy.findPath(source, destination))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 직선 이동만 가능합니다.");
        }

        @Test
        void 차와_포는_한_방향으로_이동하지_않으면_예외가_발생한다() {
            assertThatThrownBy(() -> strategy.findPath(new Position(0, 0), new Position(5, 5)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 해당 기물은 직선 이동만 가능합니다.");
        }

        @Test
        void 차와_포는_제자리_이동_시_예외가_발생한다() {
            assertThatThrownBy(() -> strategy.findPath(new Position(0, 0), new Position(0, 0)))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("[ERROR] 기물은 반드시 이동해야 합니다.");
        }
    }
}
