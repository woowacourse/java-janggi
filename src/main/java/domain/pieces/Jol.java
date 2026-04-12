package domain.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;

public class Jol extends Piece {

    public Jol(Country country) {
        super(country, PieceType.JOL);
    }

    @Override
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        List<Position> availableRoute = new ArrayList<>();
        List<Direction> directions = new ArrayList<>(Direction.getCardinalDirections());
        start.addPalaceDirection(directions);

        for (Direction direction : directions) {
            Optional<Position> position = move(start, direction);
            if (position.isEmpty() || isBackMovement(start, position.get())) continue;
            if (cantMoveDiagonalOutOfPalace(direction, position.get())) continue;

            Piece endPiece = finder.find(position.get());
            if (!canMoveToEnd(endPiece)) continue;

            availableRoute.add(position.get());
        }
        return availableRoute;
    }

    private boolean canMoveToEnd(Piece endPiece) {
        return endPiece == None.INSTANCE || isDifferentCountry(endPiece.getCountry());
    }

    private boolean isBackMovement(Position start, Position end) {
        return getCountry().getForward()==start.getX()-end.getX();
    }
    private boolean cantMoveDiagonalOutOfPalace(Direction direction, Position now) {
        return Direction.getDiagonalDirections().contains(direction) && (!now.isInPalace());
    }

}
