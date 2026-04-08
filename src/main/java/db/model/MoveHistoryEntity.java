package db.model;

import pieces.PieceType;
import pieces.Side;

public record MoveHistoryEntity(
    Long id,
    Long gameId,
    int moveOrder,
    PieceType movingPieceType,
    Side movingPieceSide,
    int departureRow,
    int departureColumn,
    int destinationRow,
    int destinationColumn,
    boolean isCapture,
    PieceType capturedPieceType,
    Side capturedPieceSide) {
}
