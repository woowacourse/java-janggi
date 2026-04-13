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
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        List<Position> availableRoute = new ArrayList<>();
        List<Direction> directions = new ArrayList<>(Direction.getCardinalDirections());
        start.addPalaceDirection(directions);

        for (Direction direction : directions) {
            Optional<Position> position = move(start, direction);
            if (checkPositionInPalace(position)) continue;

            Piece endPiece = finder.find(position.get());
            if (!canMoveToEnd(endPiece)) continue;
            availableRoute.add(position.get());
        }
        return availableRoute;
    }

    private boolean checkPositionInPalace(Optional<Position> position) {
        return (position.isEmpty() || !position.get().isInPalace());
    }

    private boolean canMoveToEnd(Piece endPiece) {
        return endPiece == None.INSTANCE || isDifferentCountry(endPiece.getCountry());
    }
}
