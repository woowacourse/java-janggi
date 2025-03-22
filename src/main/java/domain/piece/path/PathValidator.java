package domain.piece.path;

import domain.Board;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;

public interface PathValidator {

    default void validatePath(List<Position> positions, Position to, Board board, Piece movePiece){
        validateMovePath(positions, board);
        validateDestination(to, board, movePiece);
    }

    void validateMovePath(List<Position> positions, Board board);
    void validateDestination(Position to, Board board, Piece movePiece);
}
