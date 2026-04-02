package dto;

import domain.piece.Piece;
import domain.board.Position;

public record PieceInfoDto(PieceNameDto pieceName, PositionDto position) {

    public static PieceInfoDto of(final Piece piece, final Position position) {
        return new PieceInfoDto(PieceNameDto.from(piece), PositionDto.of(position));
    }
}
