package domain.path;

import domain.board.Position;
import domain.piece.Piece;
import domain.piece.PieceType;

public record PathInfo(Position position, Piece piece) {
    public boolean hasPiece(){
        return piece != null;
    }

    public boolean isSamePieceType(PieceType pieceType){
        return piece.isSameType(pieceType);
    }
}
