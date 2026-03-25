package domain.board;

import domain.piece.Piece;
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

    public Board setUp(BoardSetUp boardSetUp) {
        return new Board(boardSetUp.generate());
    }
}
