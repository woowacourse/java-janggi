package domain.piece.path;

import domain.Board;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;

public interface PathValidator {

    void validatePath(List<Position> positions, Position to, Board board, Piece movePiece);
}
