package model.navigator;

import java.util.List;
import model.position.Position;

public interface MoveStrategy<T> {
    List<Position> find(Position departure, Position arrival, T movements);
}
