package janggi.domain.board.setup;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.unit.Elephant;
import janggi.domain.piece.unit.Horse;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardSetUpTest {

    public static Stream<Arguments> generate() {
        return Stream.of(
                Arguments.of(new InSetUp(), Side.CHO, Map.of(
                        new Point(0, 1), new Horse(Side.CHO), new Point(0, 2), new Elephant(Side.CHO),
                        new Point(0, 6), new Elephant(Side.CHO), new Point(0, 7), new Horse(Side.CHO))),
                Arguments.of(new InSetUp(), Side.HAN, Map.of(
                        new Point(9, 7), new Horse(Side.HAN), new Point(9, 6), new Elephant(Side.HAN),
                        new Point(9, 2), new Elephant(Side.HAN), new Point(9, 1), new Horse(Side.HAN)
                )),
                Arguments.of(new OutSetUp(), Side.CHO, Map.of(
                        new Point(0, 1), new Elephant(Side.CHO), new Point(0, 2), new Horse(Side.CHO),
                        new Point(0, 6), new Horse(Side.CHO), new Point(0, 7), new Elephant(Side.CHO)
                )),
                Arguments.of(new OutSetUp(), Side.HAN, Map.of(
                        new Point(9, 7), new Elephant(Side.HAN), new Point(9, 6), new Horse(Side.HAN),
                        new Point(9, 2), new Horse(Side.HAN), new Point(9, 1), new Elephant(Side.HAN)
                )),
                Arguments.of(new LeftSetUp(), Side.CHO, Map.of(
                        new Point(0, 1), new Elephant(Side.CHO), new Point(0, 2), new Horse(Side.CHO),
                        new Point(0, 6), new Elephant(Side.CHO), new Point(0, 7), new Horse(Side.CHO)
                )),
                Arguments.of(new LeftSetUp(), Side.HAN, Map.of(
                        new Point(9, 7), new Elephant(Side.HAN), new Point(9, 6), new Horse(Side.HAN),
                        new Point(9, 2), new Elephant(Side.HAN), new Point(9, 1), new Horse(Side.HAN)
                )),
                Arguments.of(new RightSetUp(), Side.CHO, Map.of(
                        new Point(0, 1), new Horse(Side.CHO), new Point(0, 2), new Elephant(Side.CHO),
                        new Point(0, 6), new Horse(Side.CHO), new Point(0, 7), new Elephant(Side.CHO)
                )),
                Arguments.of(new RightSetUp(), Side.HAN, Map.of(
                        new Point(9, 7), new Horse(Side.HAN), new Point(9, 6), new Elephant(Side.HAN),
                        new Point(9, 2), new Horse(Side.HAN), new Point(9, 1), new Elephant(Side.HAN)
                ))
        );
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("generate(): 장기판 기물차림(4가지) 테스트")
    void generate(BoardSetUp boardSetUp, Side side, Map<Point, Piece> expected) {
        assertThat(boardSetUp.generate(side)).isEqualTo(expected);
    }
}
