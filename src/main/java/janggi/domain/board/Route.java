package janggi.domain.board;

import janggi.domain.piece.Piece;
import java.util.List;

public class Route {
    private final Piece sourcePiece;
    private final PiecePath path;

    public Route(Piece sourcePiece, PiecePath path) {
        validateAvailableRoute(sourcePiece, path);
        this.sourcePiece = sourcePiece;
        this.path = path;
    }

    private void validateAvailableRoute(Piece sourcePiece, PiecePath path) {
        boolean isAvailableRoute = sourcePiece.isValidMovement(path);

        if(!isAvailableRoute) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없는 기물입니다.");
        }
    }

    public List<Position> getAllRouteToDestination() {
        return sourcePiece.findAllRoute(path);
    }
}
