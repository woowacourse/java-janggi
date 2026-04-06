package janggi.exception;

import static janggi.exception.ErrorCode.ROUTE_RESOLVE_ERROR;

import janggi.domain.Location;
import janggi.domain.piece.PieceType;

public class RouteResolveException extends JanggiException {

    private static final ErrorCode defaultErrorCode = ROUTE_RESOLVE_ERROR;

    public RouteResolveException(ErrorCode defaultErrorCode) {
        super(defaultErrorCode);
    }

    public RouteResolveException(Location from, Location to) {
        super(defaultErrorCode, defaultErrorCode.getMessage() + from.toString() + " -> " + to.toString());
    }

    public RouteResolveException(PieceType pieceType, Location from, Location to) {
        super(defaultErrorCode,
                defaultErrorCode.getMessage() + " 기물: " + pieceType + ", " + from.toString() + " -> " + to.toString());
    }
}
