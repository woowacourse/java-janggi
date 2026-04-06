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
    public boolean canMovePosition(Position start, Position end) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();
        return Math.abs(diffX) + Math.abs(diffY) == 1;
    }

    @Override
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        List<Position> availableRoute = new ArrayList<>();
        for (Direction direction : Direction.getCardinalDirections()){
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

//
//    @Override
//    public boolean isAvailableRoute(List<Piece> pieces, PieceType endPieceType) {
//        return true;
//    }
}
