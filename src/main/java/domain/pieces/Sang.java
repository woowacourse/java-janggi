package domain.pieces;

import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;
import domain.Position;
import domain.strategy.StraightMovement;
import domain.strategy.MoveStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Sang extends Piece {

    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();

        return (Math.abs(diffX) == 3 && Math.abs(diffY) == 2) || (Math.abs(diffX) == 2 && Math.abs(diffY) == 3);
    }

    @Override
    public List<Position> getAvailableRoute(Position start){
        List<Position> availableRoute = new ArrayList<>();
        // TODO: 대각 구현 필요
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
