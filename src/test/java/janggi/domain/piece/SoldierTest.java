package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.coordinate.FixedPathStrategy;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SoldierTest {
    private static PathStrategy fixedPathStrategy = new FixedPathStrategy();

    public static Stream<Arguments> path() {
        return Stream.of(
                Arguments.of(Side.CHO, List.of(
                        new Pattern(List.of(Direction.NORTH), fixedPathStrategy),
                        new Pattern(List.of(Direction.EAST), fixedPathStrategy),
                        new Pattern(List.of(Direction.WEST), fixedPathStrategy))),

                Arguments.of(Side.HAN, List.of(
                        new Pattern(List.of(Direction.SOUTH), fixedPathStrategy),
                        new Pattern(List.of(Direction.EAST), fixedPathStrategy),
                        new Pattern(List.of(Direction.WEST), fixedPathStrategy)))
        );
    }

    @Test
    @DisplayName("")
    void availablePoints() {

    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("path(): 이동 경로의 상대 방향을 전달한다.")
    void path(Side side, List<Pattern> expected) {
        Soldier soldier = new Soldier(side);

        List<Pattern> pattern = soldier.directions();

        pattern.forEach( directions ->
                assertThat(expected.contains(pattern)).isTrue()
        );
    }
}
