package domain.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.PieceFinder;
import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;
import domain.Position;

public class Ma extends Piece {

    public Ma(Country country) {
        super(country, PieceType.MA);
    }

    @Override
    public boolean canMovePosition(Position start, Position end) {
        int diffX = end.getX() - start.getX();
        int diffY = end.getY() - start.getY();

        return (Math.abs(diffX) == 2 && Math.abs(diffY) == 1) || (Math.abs(diffX) == 1 && Math.abs(diffY) == 2);
    }

    @Override
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        List<Position> availableRoute = new ArrayList<>();
        for (Direction direction : Direction.getCardinalDirections()) {
            Optional<Position> positionFirst = move(start, direction);
            if (positionFirst.isEmpty()) {
                continue;
            }
            
            Piece endPiece = finder.find(positionFirst.get());
            if (endPiece!=None.INSTANCE) {
                continue;
            }
            
            for (Direction moveDirection : direction.getMaSangDiagonalDirections(direction)) {
                Optional<Position> position = move(positionFirst.get(), moveDirection);
                if (position.isEmpty()) {
                    continue;
                }
                
                endPiece = finder.find(position.get());
                if (endPiece==None.INSTANCE || isDifferentCountry(endPiece.getCountry())) {
                    position.ifPresent(availableRoute::add);
                }
            }
        }
        return availableRoute;
    }

}
