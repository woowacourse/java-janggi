package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.movement.Direction;
import janggi.domain.piece.Piece;

public interface BoardMediator {

    boolean canMove(Position position, Direction direction);

    boolean existsByPosition(Position position);

    Piece getPieceByPosition(Position position);

}
