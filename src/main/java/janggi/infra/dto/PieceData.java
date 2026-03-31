package janggi.infra.dto;

public record PieceData(
        String pieceName,
        String teamName,
        int row,
        int col
) {
}
