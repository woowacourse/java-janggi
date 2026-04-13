package domain.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;

public class Cha extends Piece {

    public Cha(Country country) {
        super(country, PieceType.CHA);
    }

    @Override
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        List<Position> availableRoute = new ArrayList<>();
        List<Direction> directions = new ArrayList<>(Direction.getCardinalDirections());
        start.addPalaceDirection(directions);

        for (Direction direction : directions) {
            availableRoute.addAll(searchOneDirection(start, finder, direction));
        }
        return availableRoute;
    }

    private List<Position> searchOneDirection(Position start, PieceFinder finder, Direction direction) {
        List<Position> availableRoute = new ArrayList<>();
        Position now = start;

        for (int i = 0; i < Position.MAX_ROW; i++) {
            Optional<Position> position = move(now, direction);
            if (position.isEmpty()) break;
            if (cantMoveDiagonalOutOfPalace(direction, position.get())) break;

            Piece endPiece = finder.find(position.get());
            if (canMoveToEndButStop(endPiece)) {
                availableRoute.add(position.get());
                break;
            }

            if (endPiece!=None.INSTANCE) continue;
            availableRoute.add(position.get());
            now = position.get();
        }
        return availableRoute;
    }

    private boolean cantMoveDiagonalOutOfPalace(Direction direction, Position now) {
        return Direction.getDiagonalDirections().contains(direction) && (!now.isInPalace());
    }

    private boolean canMoveToEndButStop(Piece endPiece) {
        return endPiece != None.INSTANCE && isDifferentCountry(endPiece.getCountry());
    }

}
