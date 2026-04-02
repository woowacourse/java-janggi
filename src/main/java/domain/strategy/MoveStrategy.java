package domain.strategy;

import java.util.Optional;

import domain.Position;
import domain.enums.Country;
import domain.enums.Direction;

public interface MoveStrategy {
    Optional<Position> move(Position start, Direction direction, Country country) ;
}
