package janggi.domain.board.setup;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.piece.Piece;
import janggi.domain.piece.fixed.Advisor;
import janggi.domain.piece.fixed.Elephant;
import janggi.domain.piece.fixed.General;
import janggi.domain.piece.fixed.Horse;
import janggi.domain.piece.fixed.Soldier;
import janggi.domain.piece.linear.Cannon;
import janggi.domain.piece.linear.Chariot;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import janggi.view.BoardSetUpFormat;
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
                Arguments.of(BoardSetUpFormat.IN_ELEPHANT.getBoardSetUp(), Side.CHO, Map.of(
                        new Point(0, 1), new Horse(Side.CHO), new Point(0, 2), new Elephant(Side.CHO),
                        new Point(0, 6), new Elephant(Side.CHO), new Point(0, 7), new Horse(Side.CHO))),

                Arguments.of(BoardSetUpFormat.IN_ELEPHANT.getBoardSetUp(), Side.HAN, Map.of(
                        new Point(9, 7), new Horse(Side.HAN), new Point(9, 6), new Elephant(Side.HAN),
                        new Point(9, 2), new Elephant(Side.HAN), new Point(9, 1), new Horse(Side.HAN)
                )),
                Arguments.of(OutElephantSetUp.INSTANCE, Side.CHO, Map.of(
                        new Point(0, 1), new Elephant(Side.CHO), new Point(0, 2), new Horse(Side.CHO),
                        new Point(0, 6), new Horse(Side.CHO), new Point(0, 7), new Elephant(Side.CHO)
                )),
                Arguments.of(OutElephantSetUp.INSTANCE, Side.HAN, Map.of(
                        new Point(9, 7), new Elephant(Side.HAN), new Point(9, 6), new Horse(Side.HAN),
                        new Point(9, 2), new Horse(Side.HAN), new Point(9, 1), new Elephant(Side.HAN)
                )),
                Arguments.of(LeftElephantSetUp.INSTANCE, Side.CHO, Map.of(
                        new Point(0, 1), new Elephant(Side.CHO), new Point(0, 2), new Horse(Side.CHO),
                        new Point(0, 6), new Elephant(Side.CHO), new Point(0, 7), new Horse(Side.CHO)
                )),
                Arguments.of(LeftElephantSetUp.INSTANCE, Side.HAN, Map.of(
                        new Point(9, 7), new Elephant(Side.HAN), new Point(9, 6), new Horse(Side.HAN),
                        new Point(9, 2), new Elephant(Side.HAN), new Point(9, 1), new Horse(Side.HAN)
                )),
                Arguments.of(RightElephantSetUp.INSTANCE, Side.CHO, Map.of(
                        new Point(0, 1), new Horse(Side.CHO), new Point(0, 2), new Elephant(Side.CHO),
                        new Point(0, 6), new Horse(Side.CHO), new Point(0, 7), new Elephant(Side.CHO)
                )),
                Arguments.of(RightElephantSetUp.INSTANCE, Side.HAN, Map.of(
                        new Point(9, 7), new Horse(Side.HAN), new Point(9, 6), new Elephant(Side.HAN),
                        new Point(9, 2), new Horse(Side.HAN), new Point(9, 1), new Elephant(Side.HAN)
                )),
                Arguments.of(LeftElephantSetUp.INSTANCE, Side.CHO, createChoCommonBoard()),
                Arguments.of(LeftElephantSetUp.INSTANCE, Side.HAN, createHanCommonBoard()),
                Arguments.of(RightElephantSetUp.INSTANCE, Side.CHO, createChoCommonBoard()),
                Arguments.of(RightElephantSetUp.INSTANCE, Side.HAN, createHanCommonBoard())

        );
    }


    private static Map<Point, Piece> createHanCommonBoard() {
        Map<Point, Piece> board = new HashMap<>();
        board.put(new Point(6, 8), new Soldier(Side.HAN));
        board.put(new Point(6, 6), new Soldier(Side.HAN));
        board.put(new Point(6, 4), new Soldier(Side.HAN));
        board.put(new Point(6, 2), new Soldier(Side.HAN));
        board.put(new Point(6, 0), new Soldier(Side.HAN));

        board.put(new Point(7, 7), new Cannon(Side.HAN));
        board.put(new Point(7, 1), new Cannon(Side.HAN));

        board.put(new Point(8, 4), new General(Side.HAN));

        board.put(new Point(9, 8), new Chariot(Side.HAN));
        board.put(new Point(9, 5), new Advisor(Side.HAN));
        board.put(new Point(9, 3), new Advisor(Side.HAN));
        board.put(new Point(9, 0), new Chariot(Side.HAN));

        return board;
    }

    private static Map<Point, Piece> createChoCommonBoard() {
        Map<Point, Piece> board = new HashMap<>();
        board.put(new Point(0, 0), new Chariot(Side.CHO));
        board.put(new Point(0, 3), new Advisor(Side.CHO));
        board.put(new Point(0, 5), new Advisor(Side.CHO));
        board.put(new Point(0, 8), new Chariot(Side.CHO));

        board.put(new Point(1, 4), new General(Side.CHO));

        board.put(new Point(2, 1), new Cannon(Side.CHO));
        board.put(new Point(2, 7), new Cannon(Side.CHO));

        board.put(new Point(3, 0), new Soldier(Side.CHO));
        board.put(new Point(3, 2), new Soldier(Side.CHO));
        board.put(new Point(3, 4), new Soldier(Side.CHO));
        board.put(new Point(3, 6), new Soldier(Side.CHO));
        board.put(new Point(3, 8), new Soldier(Side.CHO));
        return board;
    }

    private Map<Point, Piece> createDefaultBoard() {
        Map<Point, Piece> defaultBoard = new HashMap<>();

        defaultBoard.put(new Point(0, 0), new Chariot(Side.CHO));
        defaultBoard.put(new Point(0, 3), new Advisor(Side.CHO));
        defaultBoard.put(new Point(0, 5), new Advisor(Side.CHO));
        defaultBoard.put(new Point(0, 8), new Chariot(Side.CHO));

        defaultBoard.put(new Point(1, 4), new General(Side.CHO));

        defaultBoard.put(new Point(2, 1), new Cannon(Side.CHO));
        defaultBoard.put(new Point(2, 7), new Cannon(Side.CHO));

        defaultBoard.put(new Point(3, 0), new Soldier(Side.CHO));
        defaultBoard.put(new Point(3, 2), new Soldier(Side.CHO));
        defaultBoard.put(new Point(3, 4), new Soldier(Side.CHO));
        defaultBoard.put(new Point(3, 6), new Soldier(Side.CHO));
        defaultBoard.put(new Point(3, 8), new Soldier(Side.CHO));

        defaultBoard.put(new Point(6, 8), new Soldier(Side.HAN));
        defaultBoard.put(new Point(6, 6), new Soldier(Side.HAN));
        defaultBoard.put(new Point(6, 4), new Soldier(Side.HAN));
        defaultBoard.put(new Point(6, 2), new Soldier(Side.HAN));
        defaultBoard.put(new Point(6, 0), new Soldier(Side.HAN));

        defaultBoard.put(new Point(7, 7), new Cannon(Side.HAN));
        defaultBoard.put(new Point(7, 1), new Cannon(Side.HAN));

        defaultBoard.put(new Point(8, 4), new General(Side.HAN));

        defaultBoard.put(new Point(9, 8), new Chariot(Side.HAN));
        defaultBoard.put(new Point(9, 5), new Advisor(Side.HAN));
        defaultBoard.put(new Point(9, 3), new Advisor(Side.HAN));
        defaultBoard.put(new Point(9, 0), new Chariot(Side.HAN));

        return defaultBoard;
    }

    @ParameterizedTest
    @MethodSource
    @DisplayName("generate(): 장기판 기물차림(4가지) 테스트")
    void generate(BoardSetUp boardSetUp, Side side, Map<Point, Piece> expected) {
        assertThat(boardSetUp.generate(side)).containsAllEntriesOf(expected);
    }
}
