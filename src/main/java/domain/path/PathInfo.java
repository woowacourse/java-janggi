package domain.path;

import domain.board.Position;
import domain.piece.Piece;

public record PathInfo(Position position, Piece piece) {
    public boolean hasPiece(){
        return piece != null;
    }
}
