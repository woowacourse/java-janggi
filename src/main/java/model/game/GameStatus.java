package model.game;

import model.pieces.Piece;
import model.pieces.PieceType;

public class GameStatus {
    private final boolean finished;

    private GameStatus(boolean finished) {
        this.finished = finished;
    }

    public static GameStatus playing() {
        return new GameStatus(false);
    }

    public GameStatus update(Piece capturedPiece) {
        if (capturedPiece == null) {
            return this;
        }
        if (capturedPiece.pieceType() == PieceType.GENERAL) {
            return new GameStatus(true);
        }
        return this;
    }

    public void validateNotFinished() {
        if (finished) {
            throw new IllegalArgumentException("[ERROR] 이미 종료된 게임입니다.");
        }
    }
}
