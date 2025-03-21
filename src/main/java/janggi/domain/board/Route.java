package janggi.domain.board;

import janggi.domain.piece.Piece;
import java.util.List;

public class Route {
    private final Piece sourcePiece;
    private final Position source;
    private final Position destination;

    public Route(Piece sourcePiece, Position source, Position destination) {
        validateAvailableRoute(sourcePiece, source, destination);
        this.sourcePiece = sourcePiece;
        this.source = source;
        this.destination = destination;
    }

    private void validateAvailableRoute(Piece sourcePiece, Position sourcePosition, Position destinationPosition) {
        boolean isAvailableRoute = sourcePiece.isValidMovement(sourcePosition, destinationPosition);

        if(!isAvailableRoute) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없는 기물입니다.");
        }
    }

    public List<Position> getAllRouteToDestination() {
        return sourcePiece.findAllRoute(source, destination);
    }
}
