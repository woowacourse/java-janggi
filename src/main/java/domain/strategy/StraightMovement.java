package domain.strategy;

import java.util.Optional;

import domain.Position;
import domain.enums.Country;
import domain.enums.Direction;

public class StraightMovement implements MoveStrategy {
    public Optional<Position> move(Position start, Direction direction, Country country) {
        int startX = start.getX();
        int startY = start.getY();
        int forward = country.getForward();
        int dx = direction.getDx();
        int dy = direction.getDy();
        try {
            return Optional.of(Position.create(startX + dx * forward, startY + dy * forward));
        } catch(IllegalArgumentException e) {
            return Optional.empty();
        }
    }



}
