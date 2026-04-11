package team.janggi.domain.board;

import team.janggi.domain.BoardSize;
import team.janggi.domain.Position;
import team.janggi.domain.piece.Piece;

public class EmptyBoardPiecesInitializer implements BoardPiecesInitializer {
    public static final EmptyBoardPiecesInitializer instance = new EmptyBoardPiecesInitializer();

    @Override
    public void initBoardStatus(BoardPieces status) {
        initMapByEmpty(status);
    }

    private void initMapByEmpty( BoardPieces status) {
        for (int y = 0; y < BoardSize.Y; y++) {
            initMapRowByEmpty(status, y);
        }
    }

    private void initMapRowByEmpty(BoardPieces status, int y) {
        for (int x = 0; x < BoardSize.X; x++) {
            status.setPiece(new Position(x, y), Piece.EMPTY_PIECE);
        }
    }
}
