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
        List<Direction> directions = new ArrayList<>(Direction.getCardinalDirections());

        if (start.isPalaceDiagonal()){
            directions.addAll(Direction.getDiagonalDirections());
        }

        for (Direction direction : directions) {
            Optional<Position> position = move(start, direction);
            // 1) 좌표 밖이거나  2) 궁성 밖이거나 → 불가능
            if (position.isEmpty() || !position.get().isInPalace()) {
                continue;
            }

            Piece endPiece = finder.find(position.get());
            // 3) 도착지가 비어있음,  4) 다른 나라 기물 → 가능
            if (finder.find(position.get()) == None.INSTANCE || isDifferentCountry(endPiece.getCountry())) {
                availableRoute.add(position.get());
            }
        }
        return availableRoute;
    }
}
