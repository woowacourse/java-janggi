package janggi.piece;

import janggi.board.Position;
import janggi.board.Route;

import java.util.List;

public interface Piece {

    List<Position> computeCandidatePositions(Position position);

    String getSymbol();

    boolean isCho();

    boolean isHan();
}
