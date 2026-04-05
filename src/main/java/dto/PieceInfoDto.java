package dto;

import domain.piece.PieceType;
import domain.piece.Team;

public record PieceInfoDto(String pieceType, String team) {

    public static PieceInfoDto of(final PieceType pieceType, final Team team) {
        return new PieceInfoDto(pieceType.toString(), team.toString());
    }
}
