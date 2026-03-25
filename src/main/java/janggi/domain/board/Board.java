package janggi.domain.board;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.piece.Advisor;
import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.General;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
import janggi.domain.side.Side;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private static final int ROW_SIZE = 9;
    private static final int COL_SIZE = 10;

    private final Map<Point, Piece> board;

    private Board(Map<Point, Piece> board) {
//        validateSize(board);
//        validatePiece(board);
        this.board = board;
    }

//    private void validatePiece(List<List<domain.piece.Piece>> board) {
//
//    }

//    private void validateSize(Map<Point, Piece> board) {
//        if (board.size() != ROW_SIZE) {
//            throw new IllegalStateException("보드의 행은 %d여야 합니다.".formatted(ROW_SIZE));
//        }
//        board.forEach(col -> {
//            if (col.size() != COL_SIZE) {
//                throw new IllegalStateException("보드의 줄은 %d여야 합니다.".formatted(COL_SIZE));
//            }
//        });
//    }

    public Board setUp(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        Map<Point, Piece> board = createCommonBoard();
        board.putAll(choBoardSetUp.generate(Side.CHO));
        board.putAll(hanBoardSetUp.generate(Side.HAN));

        return new Board(board);
    }

    private Map<Point, Piece> createCommonBoard() {
        Map<Point, Piece> board = new HashMap<>();

        board.put(new Point(0, 0), new Chariot(Side.CHO));
        board.put(new Point(3, 0), new Advisor(Side.CHO));
        board.put(new Point(5, 0), new Advisor(Side.CHO));
        board.put(new Point(8, 0), new Chariot(Side.CHO));

        board.put(new Point(4, 1), new General(Side.CHO));

        board.put(new Point(1, 2), new Cannon(Side.CHO));
        board.put(new Point(7, 2), new Cannon(Side.CHO));

        board.put(new Point(0, 3), new Soldier(Side.CHO));
        board.put(new Point(2, 3), new Soldier(Side.CHO));
        board.put(new Point(4, 3), new Soldier(Side.CHO));
        board.put(new Point(6, 3), new Soldier(Side.CHO));
        board.put(new Point(8, 3), new Soldier(Side.CHO));

        board.put(new Point(0, 6), new Soldier(Side.HAN));
        board.put(new Point(2, 6), new Soldier(Side.HAN));
        board.put(new Point(4, 6), new Soldier(Side.HAN));
        board.put(new Point(6, 6), new Soldier(Side.HAN));
        board.put(new Point(8, 6), new Soldier(Side.HAN));

        board.put(new Point(1, 7), new Cannon(Side.HAN));
        board.put(new Point(7, 7), new Cannon(Side.HAN));

        board.put(new Point(4, 8), new General(Side.HAN));

        board.put(new Point(0, 9), new Chariot(Side.HAN));
        board.put(new Point(3, 9), new Advisor(Side.HAN));
        board.put(new Point(5, 9), new Advisor(Side.HAN));
        board.put(new Point(8, 9), new Chariot(Side.HAN));

        return board;
    }
}
