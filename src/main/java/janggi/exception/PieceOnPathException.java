package janggi.exception;

import janggi.domain.piece.Piece;

public class PieceOnPathException extends JanggiException {

    public PieceOnPathException(ErrorCode errorCode) {
        super(errorCode);
    }

    public PieceOnPathException(ErrorCode errorCode, Piece obstacle) {
        super(errorCode, " obstacle piece: " + obstacle.getType());
    }

    public PieceOnPathException(ErrorCode errorCode, long obstacleCount) {
        super(errorCode, " obstacle count: " + obstacleCount);
    }
}
