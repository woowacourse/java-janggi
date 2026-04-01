package domain;

import domain.piece.Piece;
import domain.position.Position;

public record PieceDTO(int row, int col, Team team) {
    public static PieceDTO from(Position position, Piece piece) {
        return new PieceDTO(position.getRow(), position.getColumn(), piece.getTeam());
    }
}
