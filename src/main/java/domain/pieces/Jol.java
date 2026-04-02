package domain.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.Position;
import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;
import domain.strategy.StraightMovement;
import domain.strategy.MoveStrategy;

public class Jol extends Piece {

    public Jol(Country country) {
        super(country, PieceType.JOL);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();
        if (Math.abs(diffX) + Math.abs(diffY) != 1) {
            return false;
        }
        if (getCountry().equals(Country.CHO)) {
            return diffX >= 0;
        }
        return diffX <= 0;
    }

    @Override
    public List<Position> getAvailableRoute(Position start) {
        List<Position> availableRoute = new ArrayList<>();
        for (Direction direction : List.of(Direction.UP, Direction.RIGHT, Direction.LEFT)) {
            Optional<Position> position = move(start, direction,getCountry());
            if (position.isPresent()){
                availableRoute.add(position.get());
            }
        }
        return availableRoute;
    }

    @Override
    public Optional<Position> move(Position start, Direction direction, Country country) {
        MoveStrategy moveStraight = new StraightMovement();
        return moveStraight.move(start, direction, country);
    }

    @Override
    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
        return true;
    }
}
