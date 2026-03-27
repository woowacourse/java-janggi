package janggi.domain.board;


import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.unit.Advisor;
import janggi.domain.piece.unit.Cannon;
import janggi.domain.piece.unit.Chariot;
import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Piece;
import janggi.domain.piece.unit.Soldier;
import janggi.domain.side.Side;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardTest {

    @Test
    @DisplayName("createCommonBoard(): 기본 장기판 생성")
    void createCommonBoard() {
        Map<Point, Piece> defaultBoard = createDefaultBoard();

        BoardSetUp emptyBoardSetUp = (side) -> Collections.EMPTY_MAP;

        Map<Point, Piece> board = Board.setUp(emptyBoardSetUp, emptyBoardSetUp).getBoard();

        defaultBoard.forEach((key, value) -> {
            assertThat(board.get(key)).isEqualTo(value);
        });
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

        defaultBoard.put(new Point(8, 6), new General(Side.HAN));

        defaultBoard.put(new Point(9, 8), new Chariot(Side.HAN));
        defaultBoard.put(new Point(9, 5), new Advisor(Side.HAN));
        defaultBoard.put(new Point(9, 3), new Advisor(Side.HAN));
        defaultBoard.put(new Point(9, 0), new Chariot(Side.HAN));

        return defaultBoard;
    }
}
