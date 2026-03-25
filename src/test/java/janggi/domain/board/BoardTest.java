package janggi.domain.board;


import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.Advisor;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.General;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
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
        defaultBoard.put(new Point(3, 0), new Advisor(Side.CHO));
        defaultBoard.put(new Point(5, 0), new Advisor(Side.CHO));
        defaultBoard.put(new Point(8, 0), new Chariot(Side.CHO));

        defaultBoard.put(new Point(4, 1), new General(Side.CHO));

        defaultBoard.put(new Point(1, 2), new Cannon(Side.CHO));
        defaultBoard.put(new Point(7, 2), new Cannon(Side.CHO));

        defaultBoard.put(new Point(0, 3), new Soldier(Side.CHO));
        defaultBoard.put(new Point(2, 3), new Soldier(Side.CHO));
        defaultBoard.put(new Point(4, 3), new Soldier(Side.CHO));
        defaultBoard.put(new Point(6, 3), new Soldier(Side.CHO));
        defaultBoard.put(new Point(8, 3), new Soldier(Side.CHO));

        defaultBoard.put(new Point(0, 6), new Soldier(Side.HAN));
        defaultBoard.put(new Point(2, 6), new Soldier(Side.HAN));
        defaultBoard.put(new Point(4, 6), new Soldier(Side.HAN));
        defaultBoard.put(new Point(6, 6), new Soldier(Side.HAN));
        defaultBoard.put(new Point(8, 6), new Soldier(Side.HAN));

        defaultBoard.put(new Point(1, 7), new Cannon(Side.HAN));
        defaultBoard.put(new Point(7, 7), new Cannon(Side.HAN));

        defaultBoard.put(new Point(4, 8), new General(Side.HAN));

        defaultBoard.put(new Point(0, 9), new Chariot(Side.HAN));
        defaultBoard.put(new Point(3, 9), new Advisor(Side.HAN));
        defaultBoard.put(new Point(5, 9), new Advisor(Side.HAN));
        defaultBoard.put(new Point(8, 9), new Chariot(Side.HAN));
        return defaultBoard;
    }
}
