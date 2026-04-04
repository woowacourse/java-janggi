package dto;

import domain.board.Position;
import domain.piece.PieceType;
import domain.piece.Team;

public record PiecePositionDto(String pieceType, String team, PositionDto position) {

    public static PiecePositionDto of(final PieceType pieceType, final Team team, final Position position) {
        return new PiecePositionDto(pieceType.toString(), team.toString(), PositionDto.of(position));
    }
}
