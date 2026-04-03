package janggi.exception;

import static janggi.exception.ErrorCode.ROUTE_RESOLVE_ERROR;

import janggi.domain.Location;
import janggi.domain.piece.PieceType;

public class RouteResolveException extends JanggiException {

    private static final ErrorCode errorCode = ROUTE_RESOLVE_ERROR;

    public RouteResolveException(Location from, Location to) {
        super(errorCode, errorCode.getMessage() + from.toString() + " -> " + to.toString());
    }

    public RouteResolveException(PieceType pieceType, Location from, Location to) {
        super(errorCode,
                errorCode.getMessage() + " 기물: " + pieceType + ", " + from.toString() + " -> " + to.toString());
    }
}
