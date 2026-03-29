package domain.strategy;

import domain.board.Board;
import domain.coordinate.Position;
import domain.piece.Piece;

public interface MoveRule {

    boolean isValid(Board board, Position startPosition, Position destination, Piece piece);
}
