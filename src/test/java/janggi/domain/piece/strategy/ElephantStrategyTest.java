package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.board.Position;
import janggi.domain.piece.movement.strategy.ElephantStrategy;
import janggi.domain.piece.movement.strategy.MoveStrategy;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ElephantStrategyTest {

    private final MoveStrategy strategy = new ElephantStrategy();

    private static Stream<Arguments> createPositionsAndPath() {
        return Stream.of(
                Arguments.of(new Position(6, 4), new Position(3, 2),
                        List.of(
                                new Position(5, 4),
                                new Position(4, 3),
                                new Position(3, 2)
                        )),
                Arguments.of(new Position(6, 4), new Position(4, 1),
                        List.of(
                                new Position(6, 3),
                                new Position(5, 2),
                                new Position(4, 1)
                        )),
                Arguments.of(new Position(6, 4), new Position(8, 1),
                        List.of(
                                new Position(6, 3),
                                new Position(7, 2),
                                new Position(8, 1)
                        )),
                Arguments.of(new Position(6, 4), new Position(9, 2),
                        List.of(
                                new Position(7, 4),
                                new Position(8, 3),
                                new Position(9, 2)
                        )),
                Arguments.of(new Position(6, 4), new Position(9, 6),
                        List.of(
                                new Position(7, 4),
                                new Position(8, 5),
                                new Position(9, 6)
                        )),
                Arguments.of(new Position(6, 4), new Position(8, 7),
                        List.of(
                                new Position(6, 5),
                                new Position(7, 6),
                                new Position(8, 7)
                        )),
                Arguments.of(new Position(6, 4), new Position(4, 7),
                        List.of(
                                new Position(6, 5),
                                new Position(5, 6),
                                new Position(4, 7)
                        )),
                Arguments.of(new Position(6, 4), new Position(3, 6),
                        List.of(
                                new Position(5, 4),
                                new Position(4, 5),
                                new Position(3, 6)
                        ))
        );
    }

    @ParameterizedTest
    @MethodSource("createPositionsAndPath")
    void 상은_직선_1칸_이동_후_대각선_2칸_이동한다(Position source, Position destination, List<Position> expectedPath) {
        // when
        List<Position> path = strategy.findPath(source, destination);
        // then
        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(path).hasSize(expectedPath.size());
            assertSoftly.assertThat(path).containsExactlyElementsOf(expectedPath);
        });
    }

    private static Stream<Arguments> createExceptionPosition() {
        return Stream.of(
                Arguments.of(new Position(6, 4), new Position(3, 4)),
                Arguments.of(new Position(6, 4), new Position(6, 4))
        );
    }

    @ParameterizedTest
    @MethodSource("createExceptionPosition")
    void 상은_행마법_대로_움직이지_않으면_예외가_발생한다(Position source, Position destination) {
        assertThatThrownBy(() -> strategy.findPath(source, destination))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물은 직선 1칸 이동 후 대각선 2칸 이동만 가능합니다.");
    }
}
