package dao;

public record BoardPieceRawData(
        int rowPos,
        int colPos,
        String pieceType,
        String team
) {
}
