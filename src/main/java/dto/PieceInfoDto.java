package dto;

import domain.piece.Piece;
import domain.piece.Position;
import domain.player.Team;

public record PieceInfoDto(Team team, Position position, String pieceType) {

    public static PieceInfoDto of(Piece piece, Position position) {
        return new PieceInfoDto(piece.getTeam(), position, piece.getPieceType().getNameOf(piece.getTeam()));
    }
}
