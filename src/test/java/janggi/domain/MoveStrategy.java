package janggi.domain;

import java.util.List;

public interface MoveStrategy {

    List<Position> findPath(Position from, Position to);
}
