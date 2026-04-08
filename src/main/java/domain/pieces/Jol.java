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
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        List<Position> availableRoute = new ArrayList<>();
        List<Direction> directions = new ArrayList<>(Direction.getCardinalDirections());
        start.addPalaceDirection(directions);

        for (Direction direction : directions) {
            Optional<Position> position = move(start, direction);
            if (position.isEmpty() || isBackMovement(start, position.get())) {
                continue;
            }

            Piece endPiece = finder.find(position.get());
            if (Direction.getDiagonalDirections().contains(direction) && (!position.get().isInPalace())){
                continue;
            }

            if (finder.find(position.get())==None.INSTANCE || isDifferentCountry(endPiece.getCountry())) {
                availableRoute.add(position.get());
            }

        }
        return availableRoute;
    }

    private boolean isBackMovement(Position start, Position end) {
        return getCountry().getForward()==start.getX()-end.getX();
    }

}
