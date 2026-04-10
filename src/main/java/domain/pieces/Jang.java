package domain.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;

public class Jang extends Piece {

    public Jang(Country country) {
        super(country, PieceType.JANG);
    }

    @Override
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        List<Position> availableRoute = new ArrayList<>();
        List<Direction> directions = new ArrayList<>(Direction.getCardinalDirections());
        start.addPalaceDirection(directions);

        for (Direction direction : directions) {
            Optional<Position> position = move(start, direction);
            if (position.isEmpty() || !position.get().isInPalace()) {
                continue;
            }
            Piece endPiece = finder.find(position.get());
            if (finder.find(position.get()) == None.INSTANCE || isDifferentCountry(endPiece.getCountry())) {
                availableRoute.add(position.get());
            }
        }
        return availableRoute;
    }
}
