package db.model;

import java.time.LocalDateTime;
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
    Side capturedPieceSide,
    Integer capturedRow,
    Integer capturedColumn,
    LocalDateTime createdAt) {
}
