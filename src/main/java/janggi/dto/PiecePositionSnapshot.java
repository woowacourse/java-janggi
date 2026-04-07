package janggi.dto;

public record PiecePositionSnapshot(
        String side,
        String pieceType,
        String pieceNumber,
        int rowIndex,
        int columnIndex
) {
}
