package domain.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;

public class Sa extends Piece {

    public Sa(Country country) {
        super(country, PieceType.SA);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();
        return Math.abs(diffX) + Math.abs(diffY) == 1;
    }

    @Override
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        List<Position> availableRoute = new ArrayList<>();
        List<Direction> directions = new ArrayList<>(Direction.getCardinalDirections());
        start.addPalaceDirection(directions);

        for (Direction direction : directions) {
            Optional<Position> position = move(start, direction);
            if (position.isEmpty()) {
                continue;
            }
            Piece endPiece = finder.find(position.get());
            if (finder.find(position.get())==None.INSTANCE || isDifferentCountry(endPiece.getCountry())) {
                availableRoute.add(position.get());
            }
        }
        return availableRoute;
    }
}
