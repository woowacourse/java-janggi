package janggi.piece.strategy;

import janggi.Path;
import janggi.Position;

public interface BasicMovable {

    Path move(Position currentPosition, Position arrivalPosition);
}
