package dto;

import domain.piece.Piece;
import domain.player.Team;

public record PieceInfoDto(Team team, String pieceType) {

    public static PieceInfoDto from(final Piece piece) {
        Team team = piece.getTeam();
        return new PieceInfoDto(team, piece.getPieceTypeNameBy(team));
    }
}
