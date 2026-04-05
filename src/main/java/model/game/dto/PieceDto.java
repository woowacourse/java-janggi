package model.game.dto;

public record PieceDto(
        String pieceType,
        String team,
        int rowIndex,
        int colIndex) {
}
