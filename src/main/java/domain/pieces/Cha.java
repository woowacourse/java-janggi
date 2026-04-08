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
    public boolean canMovePosition(Position start, Position end) {
        return start.getX() == end.getX() || start.getY() == end.getY();
    }

    @Override
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        List<Position> availableRoute = new ArrayList<>();
        List<Direction> directions = new ArrayList<>(Direction.getCardinalDirections());
        start.addPalaceDirection(directions);

        for (Direction direction : directions) {
            int i = Position.MAX_ROW;
            Position now = start;
            while (i-- > 0) {
                Optional<Position> position = move(now, direction);
                if (position.isEmpty() || (Direction.getDiagonalDirections().contains(direction) && (!position.get().isInPalace()))){
                    break;
                }

                Piece endPiece = finder.find(position.get());
                if (endPiece==None.INSTANCE) {
                    availableRoute.add(position.get());
                    now = position.get();
                    continue;
                }

                if (isDifferentCountry(endPiece.getCountry())) {
                    availableRoute.add(position.get());
                    break;
                }
            }

        }
        return availableRoute;
    }

}
