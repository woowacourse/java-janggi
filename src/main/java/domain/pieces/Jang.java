package domain.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;
import domain.Position;
import domain.strategy.StraightMovement;
import domain.strategy.MoveStrategy;

public class Jang extends Piece{

    public Jang(Country country) {
        super(country,PieceType.JANG);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();

        return Math.abs(diffX) + Math.abs(diffY) == 1;
    }

    @Override
    public List<Position> getAvailableRoute(Position start) {
        List<Position> availableRoute = new ArrayList<>();
        int startX = start.getX();
        int startY = start.getY();

        for (Direction direction : List.of(Direction.UP,Direction.RIGHT,Direction.LEFT,Direction.DOWN)){
            try {
                int forward = getCountry().getForward();
                int dx = direction.getDx();
                int dy = direction.getDy();
                availableRoute.add(Position.create(startX + dx * forward, startY + dy * forward));
            }catch (IllegalArgumentException e){
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
