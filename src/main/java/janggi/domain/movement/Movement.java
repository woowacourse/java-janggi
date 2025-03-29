package janggi.domain.movement;

import janggi.domain.Coordinate;
import janggi.domain.board.PieceSearcher;

public interface Movement {

    boolean canMove(Coordinate departure, Coordinate arrival, PieceSearcher pieceSearcher);
}
