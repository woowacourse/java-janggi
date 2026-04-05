package dto;

import domain.piece.Piece;

public record PieceData(String pieceType, String team) {
    public static PieceData from(Piece piece) {
        return new PieceData(piece.getPieceType().name(), piece.getTeam().name());
    }
}
