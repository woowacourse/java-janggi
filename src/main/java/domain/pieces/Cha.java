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

public class Cha extends Piece {

    public Cha(Country country) {
        super(country, PieceType.CHA);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        return start.getX() == end.getX() || start.getY() == end.getY();
    }

    @Override
    public List<Position> getAvailableRoute(Position start,Direction direction) {
        List<Position> availableRoute = new ArrayList<>();
        int i = Position.MAX_ROW;
        Position now = start;
        while (i-- > 0) {
            Optional<Position> position = move(now, direction, getCountry());
            if (position.isPresent()) {
                availableRoute.add(position.get());
                now = position.get();
                continue;
            }
            break;
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
        int count = 0;
        for (Piece piece : pieces) {
            if (piece.getPieceType() != PieceType.NONE) {
                count++;
            }
        }
        if (!endPieceType.equals(PieceType.NONE)) {
            count--;
        }
        return !(count >= 1);
    }
}
