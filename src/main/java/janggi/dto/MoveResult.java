package janggi.dto;

import janggi.MoveStatus;

public record MoveResult(MoveStatus moveStatus, PieceMove pieceMove) {

    public static MoveResult exit() {
        return new MoveResult(MoveStatus.EXIT, null);
    }

    public static MoveResult moveCompleted(final PieceMove pieceMove) {
        return new MoveResult(MoveStatus.MOVE_COMPLETED, pieceMove);
    }

    public boolean isExit() {
        return moveStatus == MoveStatus.EXIT;
    }
}
