package janggi.board.strategy;

import janggi.board.Position;
import janggi.piece.Piece;

import java.util.Map;

public interface PlaceStrategy {

    Map<Position, Piece> initialize();
}
