package domain.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;
import domain.Position;
import domain.strategy.DiagonalMovement;
import domain.strategy.StraightMovement;
import domain.strategy.MoveStrategy;

public class Ma extends Piece {

    public Ma(Country country) {
        super(country, PieceType.MA);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();

        return (Math.abs(diffX) == 2 && Math.abs(diffY) == 1) || (Math.abs(diffX) == 1 && Math.abs(diffY) == 2);
    }
//Direction.DOWN_LEFT,Direction.DOWN_RIGHT,
//            Direction.LEFT_UP, Direction.LEFT_DOWN,Direction.RIGHT_UP,Direction.RIGHT_DOWN
    @Override
    public List<Position> getAvailableRoute(Position start) {
        List<Position> availableRoute = new ArrayList<>();

        Optional<Position> positionFirst = move(start, Direction.UP,getCountry());
        for (Direction direction : List.of(Direction.UP_LEFT, Direction.UP_RIGHT)) {
            if (positionFirst.isPresent()){
                Optional<Position> position = move(positionFirst.get(), direction,getCountry());
                if (position.isPresent()){
                    availableRoute.add(position.get());
                }
            }
        }

        positionFirst = move(start, Direction.DOWN,getCountry());
        for (Direction direction : List.of(Direction.DOWN_LEFT, Direction.DOWN_RIGHT)) {
            if (positionFirst.isPresent()){
                Optional<Position> position = move(positionFirst.get(), direction,getCountry());
                if (position.isPresent()){
                    availableRoute.add(position.get());
                }
            }
        }

        positionFirst = move(start, Direction.RIGHT,getCountry());
        for (Direction direction : List.of(Direction.RIGHT_DOWN, Direction.RIGHT_UP)) {
            if (positionFirst.isPresent()){
                Optional<Position> position = move(positionFirst.get(), direction,getCountry());
                if (position.isPresent()){
                    availableRoute.add(position.get());
                }
            }
        }

        positionFirst = move(start, Direction.LEFT,getCountry());
        for (Direction direction : List.of(Direction.LEFT_UP, Direction.LEFT_DOWN)) {
            if (positionFirst.isPresent()){
                Optional<Position> position = move(positionFirst.get(), direction,getCountry());
                if (position.isPresent()){
                    availableRoute.add(position.get());
                }
            }
        }
        return availableRoute;
    }

    @Override
    public Optional<Position> move(Position start, Direction direction, Country country) {
        MoveStrategy moveStraight = new StraightMovement();
        MoveStrategy moveDiagonal = new DiagonalMovement();
        Optional<Position> position = moveStraight.move(start, direction, country);
        if (position.isPresent()) {
            return moveDiagonal.move(start, direction, country);
        }
        return Optional.empty();
    }

    @Override
    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return true;
    }
}
