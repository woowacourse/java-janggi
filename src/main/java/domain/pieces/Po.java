package domain.pieces;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import domain.PieceFinder;
import domain.Position;
import domain.enums.Country;
import domain.enums.Direction;
import domain.enums.PieceType;

public class Po extends Piece {

    public Po(Country country) {
        super(country, PieceType.PO);
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
            List<Piece> pieces = new ArrayList<>();
            int i = Position.MAX_ROW;
            Position now = start;
            while (i-- > 0) {
                Optional<Position> position = move(now, direction);
                if (position.isEmpty()) {
                    break;
                }
                now = position.get();
                if (Direction.getDiagonalDirections().contains(direction) && (!now.isInPalace())){
                    break;
                }
                Piece endPiece = finder.find(position.get());
                if (isNotPo(endPiece) && isDifferentCountry(endPiece.getCountry()) && hasNotPoAmongPath(pieces) && hasOnePieceAmongPath(pieces)) {
                    availableRoute.add(position.get());
                }
                pieces.add(endPiece);
            }
        }
        return availableRoute;
    }

    private boolean isNotPo(Piece piece) {
        return !(piece.getPieceType() == PieceType.PO);
    }

    private boolean hasOnePieceAmongPath(List<Piece> pieces) {
        int count = 0;
        for (Piece piece : pieces) {
            if (piece.getPieceType() != PieceType.NONE) {
                count++;
            }
        }
        return count == 1;
    }

    private boolean hasNotPoAmongPath(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece.getPieceType() == PieceType.PO) {
                return false;
            }
        }
        return true;
    }

}
