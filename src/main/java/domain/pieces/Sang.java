package domain.pieces;

import domain.PieceFinder;
import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;
import domain.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Sang extends Piece {

    public Sang(Country country) {
        super(country, PieceType.SANG);
    }

    @Override
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        List<Position> availableRoute = new ArrayList<>();
        for (Direction direction : Direction.getCardinalDirections()) {
            Optional<Position> positionFirst = getPosition(start, finder, direction);
            if (positionFirst.isEmpty()) continue;
            Position position = positionFirst.get();
            availableRoute.addAll(getCanMovePositions(finder, direction, position));
        }
        return availableRoute;
    }

    private List<Position> getCanMovePositions(PieceFinder finder, Direction direction, Position positionFirst) {
        List<Position> availableRoute = new ArrayList<>();
        for (Direction moveDirection : direction.getMaSangDiagonalDirections(direction)) {
            Optional<Position> position = getPosition(positionFirst, finder, moveDirection);
            position = move(position.get(), moveDirection);
            if (position.isEmpty()) continue;

            Piece endPiece = finder.find(position.get());
            if (endPiece==None.INSTANCE || isDifferentCountry(endPiece.getCountry())) {
                position.ifPresent(availableRoute::add);
            }
        }
        return availableRoute;
    }

    private Optional<Position> getPosition(Position start, PieceFinder finder, Direction direction) {
        Optional<Position> positionFirst = move(start, direction);
        if (positionFirst.isEmpty()) {
            return Optional.empty();
        }
        Piece endPiece = finder.find(positionFirst.get());
        if (endPiece!=None.INSTANCE) {
            return Optional.empty();
        }
        return positionFirst;
    }
}
