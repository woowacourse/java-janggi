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
    public List<Position> getAvailableRoute(Position start, PieceFinder finder) {
        List<Position> availableRoute = new ArrayList<>();
        List<Direction> directions = new ArrayList<>(Direction.getCardinalDirections());
        start.addPalaceDirection(directions);

        for (Direction direction : directions) {
            availableRoute.addAll(searchOneDirection(start, finder, direction));
        }
        return availableRoute;
    }

    private List<Position> searchOneDirection(Position start, PieceFinder finder, Direction direction) {
        List<Position> availableRoute = new ArrayList<>();
        List<Piece> pieces = new ArrayList<>();
        Position now = start;

        for (int i = 0; i < Position.MAX_ROW; i++) {
            Optional<Position> position = move(now, direction);
            if (position.isEmpty()) break;
            now = position.get();

            if (cantMoveDiagonalOutOfPalace(direction, now)) break;
            Piece endPiece = finder.find(now);

            if (checkEndPiece(endPiece) && checkPieceAmongRoute(pieces)) {
                availableRoute.add(now);
            }
            pieces.add(endPiece);
        }
        return availableRoute;
    }

    private boolean cantMoveDiagonalOutOfPalace(Direction direction, Position now) {
        return Direction.getDiagonalDirections().contains(direction) && (!now.isInPalace());
    }

    private boolean checkEndPiece(Piece endPiece) {
        return isNotPo(endPiece) && isDifferentCountry(endPiece.getCountry());
    }

    private boolean checkPieceAmongRoute(List<Piece> pieces) {
        return hasNotPoAmongPath(pieces) && hasOnePieceAmongPath(pieces);
    }

    private boolean isNotPo(Piece piece) {
        return !(piece.getPieceType() == PieceType.PO);
    }

    private boolean hasOnePieceAmongPath(List<Piece> pieces) {
        int count = 0;
        for (Piece piece : pieces) {
            if (piece.getPieceType() != PieceType.NONE) count++;
        }
        return count == 1;
    }

    private boolean hasNotPoAmongPath(List<Piece> pieces) {
        for (Piece piece : pieces) {
            if (piece.getPieceType() == PieceType.PO) return false;
        }
        return true;
    }

}
