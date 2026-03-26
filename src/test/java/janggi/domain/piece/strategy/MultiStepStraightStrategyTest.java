package janggi.domain.piece.strategy;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MultiStepStraightStrategyTest {

    private final MoveStrategy strategy = new MultiStepStraightStrategy();

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
                )
        );
    }

    @ParameterizedTest
    @MethodSource("createPositionsAndPath")
    void 차는_한_방향으로만_1칸_이상_이동_할_수_있다(Position from, Position to, int size, List<Position> expectedPath) {
        List<Position> path = strategy.findPath(from, to, Camp.HAN);

        SoftAssertions.assertSoftly(assertSoftly -> {
            assertSoftly.assertThat(path).hasSize(size);
            assertSoftly.assertThat(path).containsExactlyElementsOf(expectedPath);
        });
    }

    @Test
    void 차는_한_방향으로_이동하지_않으면_예외가_발생한다() {
        assertThatThrownBy(() -> strategy.findPath(new Position(0, 0), new Position(5, 5), Camp.CHO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 기물이 이동할 수 없는 위치입니다.");
    }
}
