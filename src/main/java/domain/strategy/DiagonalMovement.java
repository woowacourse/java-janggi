package domain.strategy;

import java.util.Optional;

import domain.Position;
import domain.enums.Country;
import domain.enums.Direction;

public class DiagonalMovement implements MoveStrategy{
    // TODO : 대각선 로직으로 수정 필요
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
