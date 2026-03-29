package domain.rule;

import domain.board.Board;
import domain.coordinate.Position;
import domain.piece.Piece;

public interface MoveRule {

    boolean isValid(Board board, Position start, Position dest, Piece piece);
}
