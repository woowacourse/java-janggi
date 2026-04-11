package team.janggi.domain;

import team.janggi.domain.board.BoardPieces;
import team.janggi.domain.board.BoardPiecesInitializer;
import team.janggi.domain.piece.Piece;

public class EmptyBoardPiecesInitializer implements BoardPiecesInitializer {
    private static final int NORMAL_BOARD_Y_SIZE = 10;
    private static final int NORMAL_BOARD_X_SIZE = 9;

    @Override
    public void initBoardStatus(BoardPieces status) {
        initMapByEmpty(status);
    }

    private void initMapByEmpty( BoardPieces status) {
        for (int y = 0; y < NORMAL_BOARD_Y_SIZE; y++) {
            initMapRowByEmpty(status, y);
        }
    }

    private void initMapRowByEmpty(BoardPieces status, int y) {
        for (int x = 0; x < NORMAL_BOARD_X_SIZE; x++) {
            status.setPiece(new Position(x, y), Piece.EMPTY_PIECE);
        }
    }
}
