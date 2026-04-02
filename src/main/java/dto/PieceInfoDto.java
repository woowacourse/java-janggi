package dto;

import domain.piece.Piece;
import domain.board.Position;

public record PieceInfoDto(String pieceName, PositionDto position) {

    public static PieceInfoDto of(Piece piece, Position position) {
        if (piece.isChoPiece()) {
            return new PieceInfoDto(piece.getNameForCho(), PositionDto.of(position));
        }
        return new PieceInfoDto(piece.getNameForHan(), PositionDto.of(position));
    }
}
