package dto;

import domain.board.Position;
import domain.piece.PieceInfo;

public record PieceSaveInfo(
        Position position,
        PieceInfo pieceInfo
) {
    public static PieceSaveInfo of(Position position, PieceInfo pieceInfo) {
        return new PieceSaveInfo(position, pieceInfo);
    }
}
