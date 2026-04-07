package domain;

import java.util.Optional;

@FunctionalInterface
public interface MovingFunction {

    Optional<Position> move(Position position);
}
