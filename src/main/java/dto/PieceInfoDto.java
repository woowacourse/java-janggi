package dto;

import domain.piece.Piece;
import domain.piece.Position;

public record PieceInfoDto(String pieceName, PositionDto position) {

    public static PieceInfoDto of(Piece piece, Position position) {
        if (piece.isChoPiece()) {
            return new PieceInfoDto(piece.getNameForCho(), PositionDto.from(position));
        }
        return new PieceInfoDto(piece.getNameForHan(), PositionDto.from(position));
    }
}
