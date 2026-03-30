package janggi.domain.board.setup;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.point.Point;
import janggi.domain.piece.unit.Advisor;
import janggi.domain.piece.unit.Cannon;
import janggi.domain.piece.unit.Chariot;
import janggi.domain.piece.unit.Elephant;
import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Horse;
import janggi.domain.piece.unit.Piece;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class BoardSetUpTest {

    public static Stream<Arguments> generate() {
        return Stream.of(
                Arguments.of(new InElephantSetUp(), Side.CHO, Map.of(
                        Point.of(0, 1), new Horse(Side.CHO), Point.of(0, 2), new Elephant(Side.CHO),
                        Point.of(0, 6), new Elephant(Side.CHO), Point.of(0, 7), new Horse(Side.CHO))),

                Arguments.of(new InElephantSetUp(), Side.HAN, Map.of(
                        Point.of(9, 7), new Horse(Side.HAN), Point.of(9, 6), new Elephant(Side.HAN),
                        Point.of(9, 2), new Elephant(Side.HAN), Point.of(9, 1), new Horse(Side.HAN)
                )),
                Arguments.of(new OutElephantSetUp(), Side.CHO, Map.of(
                        Point.of(0, 1), new Elephant(Side.CHO), Point.of(0, 2), new Horse(Side.CHO),
                        Point.of(0, 6), new Horse(Side.CHO), Point.of(0, 7), new Elephant(Side.CHO)
                )),
                Arguments.of(new OutElephantSetUp(), Side.HAN, Map.of(
                        Point.of(9, 7), new Elephant(Side.HAN), Point.of(9, 6), new Horse(Side.HAN),
                        Point.of(9, 2), new Horse(Side.HAN), Point.of(9, 1), new Elephant(Side.HAN)
                )),
                Arguments.of(new LeftElephantSetUp(), Side.CHO, Map.of(
                        Point.of(0, 1), new Elephant(Side.CHO), Point.of(0, 2), new Horse(Side.CHO),
                        Point.of(0, 6), new Elephant(Side.CHO), Point.of(0, 7), new Horse(Side.CHO)
                )),
                Arguments.of(new LeftElephantSetUp(), Side.HAN, Map.of(
                        Point.of(9, 7), new Elephant(Side.HAN), Point.of(9, 6), new Horse(Side.HAN),
                        Point.of(9, 2), new Elephant(Side.HAN), Point.of(9, 1), new Horse(Side.HAN)
                )),
                Arguments.of(new RightElephantSetUp(), Side.CHO, Map.of(
                        Point.of(0, 1), new Horse(Side.CHO), Point.of(0, 2), new Elephant(Side.CHO),
                        Point.of(0, 6), new Horse(Side.CHO), Point.of(0, 7), new Elephant(Side.CHO)
                )),
                Arguments.of(new RightElephantSetUp(), Side.HAN, Map.of(
                        Point.of(9, 7), new Horse(Side.HAN), Point.of(9, 6), new Elephant(Side.HAN),
                        Point.of(9, 2), new Horse(Side.HAN), Point.of(9, 1), new Elephant(Side.HAN)
                )),
                Arguments.of(new LeftElephantSetUp(), Side.CHO, createChoCommonBoard()),
                Arguments.of(new LeftElephantSetUp(), Side.HAN, createHanCommonBoard()),
                Arguments.of(new RightElephantSetUp(), Side.CHO, createChoCommonBoard()),
                Arguments.of(new RightElephantSetUp(), Side.HAN, createHanCommonBoard())

        );
    }


    private static Map<Point, Piece> createHanCommonBoard() {
        Map<Point, Piece> board = new HashMap<>();
        board.put(Point.of(6, 8), new Soldier(Side.HAN));
        board.put(Point.of(6, 6), new Soldier(Side.HAN));
        board.put(Point.of(6, 4), new Soldier(Side.HAN));
        board.put(Point.of(6, 2), new Soldier(Side.HAN));
        board.put(Point.of(6, 0), new Soldier(Side.HAN));

        board.put(Point.of(7, 7), new Cannon(Side.HAN));
        board.put(Point.of(7, 1), new Cannon(Side.HAN));

        board.put(Point.of(8, 4), new General(Side.HAN));

        board.put(Point.of(9, 8), new Chariot(Side.HAN));
        board.put(Point.of(9, 5), new Advisor(Side.HAN));
        board.put(Point.of(9, 3), new Advisor(Side.HAN));
        board.put(Point.of(9, 0), new Chariot(Side.HAN));

        return board;
    }

    private static Map<Point, Piece> createChoCommonBoard() {
        Map<Point, Piece> board = new HashMap<>();
        board.put(Point.of(0, 0), new Chariot(Side.CHO));
        board.put(Point.of(0, 3), new Advisor(Side.CHO));
        board.put(Point.of(0, 5), new Advisor(Side.CHO));
        board.put(Point.of(0, 8), new Chariot(Side.CHO));

        board.put(Point.of(1, 4), new General(Side.CHO));

        board.put(Point.of(2, 1), new Cannon(Side.CHO));
        board.put(Point.of(2, 7), new Cannon(Side.CHO));

        board.put(Point.of(3, 0), new Soldier(Side.CHO));
        board.put(Point.of(3, 2), new Soldier(Side.CHO));
        board.put(Point.of(3, 4), new Soldier(Side.CHO));
        board.put(Point.of(3, 6), new Soldier(Side.CHO));
        board.put(Point.of(3, 8), new Soldier(Side.CHO));
        return board;
    }

    private Map<Point, Piece> createDefaultBoard() {
        Map<Point, Piece> defaultBoard = new HashMap<>();

        defaultBoard.put(Point.of(0, 0), new Chariot(Side.CHO));
        defaultBoard.put(Point.of(0, 3), new Advisor(Side.CHO));
        defaultBoard.put(Point.of(0, 5), new Advisor(Side.CHO));
        defaultBoard.put(Point.of(0, 8), new Chariot(Side.CHO));

        defaultBoard.put(Point.of(1, 4), new General(Side.CHO));

        defaultBoard.put(Point.of(2, 1), new Cannon(Side.CHO));
        defaultBoard.put(Point.of(2, 7), new Cannon(Side.CHO));

        defaultBoard.put(Point.of(3, 0), new Soldier(Side.CHO));
        defaultBoard.put(Point.of(3, 2), new Soldier(Side.CHO));
        defaultBoard.put(Point.of(3, 4), new Soldier(Side.CHO));
        defaultBoard.put(Point.of(3, 6), new Soldier(Side.CHO));
        defaultBoard.put(Point.of(3, 8), new Soldier(Side.CHO));

        defaultBoard.put(Point.of(6, 8), new Soldier(Side.HAN));
        defaultBoard.put(Point.of(6, 6), new Soldier(Side.HAN));
        defaultBoard.put(Point.of(6, 4), new Soldier(Side.HAN));
        defaultBoard.put(Point.of(6, 2), new Soldier(Side.HAN));
        defaultBoard.put(Point.of(6, 0), new Soldier(Side.HAN));

        defaultBoard.put(Point.of(7, 7), new Cannon(Side.HAN));
        defaultBoard.put(Point.of(7, 1), new Cannon(Side.HAN));

        defaultBoard.put(Point.of(8, 4), new General(Side.HAN));

        defaultBoard.put(Point.of(9, 8), new Chariot(Side.HAN));
        defaultBoard.put(Point.of(9, 5), new Advisor(Side.HAN));
        defaultBoard.put(Point.of(9, 3), new Advisor(Side.HAN));
        defaultBoard.put(Point.of(9, 0), new Chariot(Side.HAN));

        return defaultBoard;
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("generate(): 장기판 기물차림(4가지) 테스트")
    void generate(BoardSetUp boardSetUp, Side side, Map<Point, Piece> expected) {
        assertThat(boardSetUp.generate(side)).containsAllEntriesOf(expected);
    }
}
