package domain.strategy;

import domain.board.Board;
import domain.coordinate.Position;
import domain.piece.Piece;

import java.util.List;

public interface MoveStrategy {

    List<Position> generate(Board board, Position startPosition, Piece piece);
}
