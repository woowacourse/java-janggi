package janggi.piece;

import janggi.board.Position;

import java.util.List;

public interface Piece {

    List<Position> computeCandidatePositions(Position position);

    String getSymbol();

    boolean isCho();

    boolean isHan();
}
