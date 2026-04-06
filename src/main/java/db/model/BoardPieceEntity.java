package db.model;

import java.time.LocalDateTime;
import pieces.PieceType;
import pieces.Side;

public record BoardPieceEntity(
    Long id,
    Long gameId,
    int row,
    int column,
    PieceType pieceType,
    Side pieceSide,
    LocalDateTime createdAt,
    LocalDateTime updatedAt) {
}
