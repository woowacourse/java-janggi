package dto;

import domain.piece.Piece;
import domain.position.Position;

public record BoardPieceData(int row, int column, PieceData pieceData) {
    public static BoardPieceData from(Position position, Piece piece) {
        return new BoardPieceData(position.row(), position.column(), PieceData.from(piece));
    }
}
