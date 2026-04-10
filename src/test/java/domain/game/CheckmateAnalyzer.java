package domain.game;

import domain.board.Board;
import domain.board.BoardPiece;
import domain.board.Position;
import domain.piece.Camp;

public class CheckmateAnalyzer {
    private static final int MIN_X = 1;
    private static final int MAX_X = 9;
    private static final int MIN_Y = 1;
    private static final int MAX_Y = 10;

    private CheckmateAnalyzer() {
    }

    public static boolean isCheckmate(Board board, Camp camp) {
        if (!ThreatAnalyzer.isInCheck(board, camp)) {
            return false;
        }

        return !hasAnyLegalMove(board, camp);
    }

    private static boolean hasAnyLegalMove(Board board, Camp camp) {
        for (BoardPiece boardPiece : board.pieces()) {
            if (boardPiece.camp() != camp) {
                continue;
            }

            Position from = boardPiece.position();

            for (int x = MIN_X; x <= MAX_X; x++) {
                for (int y = MIN_Y; y <= MAX_Y; y++) {
                    Position to = new Position(x, y);

                    if (LegalMoveAnalyzer.isLegal(board, from, to)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
