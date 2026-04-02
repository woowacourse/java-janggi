package domain.dto;

import domain.Team;
import domain.piece.Piece;
import domain.position.Position;

public record PieceDTO(int row, int col, Team team) {
    public static PieceDTO from(Position position, Piece piece) {
        return new PieceDTO(position.row(), position.col(), piece.getTeam());
    }
}
