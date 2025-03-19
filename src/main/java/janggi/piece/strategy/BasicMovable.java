package janggi.piece.strategy;

import janggi.position.Path;
import janggi.position.Position;

public interface BasicMovable {

    Path move(Position currentPosition, Position arrivalPosition);
}
