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


    public Optional<Position> moveUpRightDiagonal(Position start, Country country) {
        Optional<Position> position = move(start, Direction.UP, country);
        if (position.isPresent()){
            return move(position.get(), Direction.RIGHT, country);
        }
        return Optional.empty();
    }

    public Optional<Position> moveUpLeftDiagonal(Position start, Country country) {
        Optional<Position> position = move(start, Direction.UP, country);
        if (position.isPresent()){
            return move(position.get(), Direction.LEFT, country);
        }
        return Optional.empty();
    }

    public Optional<Position> moveDownRightDiagonal(Position start, Country country) {
        Optional<Position> position = move(start, Direction.DOWN, country);
        if (position.isPresent()){
            return move(position.get(), Direction.RIGHT, country);
        }
        return Optional.empty();
    }

    public Optional<Position> moveDownLeftDiagonal(Position start, Country country) {
        Optional<Position> position = move(start, Direction.DOWN, country);
        if (position.isPresent()){
            return move(position.get(), Direction.LEFT, country);
        }
        return Optional.empty();
    }

    public Optional<Position> moveRightUpDiagonal(Position start, Country country) {
        Optional<Position> position = move(start, Direction.RIGHT, country);
        if (position.isPresent()){
            return move(position.get(), Direction.UP, country);
        }
        return Optional.empty();
    }

    public Optional<Position> moveRightDownDiagonal(Position start, Country country) {
        Optional<Position> position = move(start, Direction.RIGHT, country);
        if (position.isPresent()){
            return move(position.get(), Direction.DOWN, country);
        }
        return Optional.empty();
    }

    public Optional<Position> moveLeftUpDiagonal(Position start, Country country) {
        Optional<Position> position = move(start, Direction.LEFT, country);
        if (position.isPresent()){
            return move(position.get(), Direction.UP, country);
        }
        return Optional.empty();
    }

    public Optional<Position> moveLeftDownDiagonal(Position start, Country country) {
        Optional<Position> position = move(start, Direction.LEFT, country);
        if (position.isPresent()){
            return move(position.get(), Direction.DOWN, country);
        }
        return Optional.empty();
    }
}
