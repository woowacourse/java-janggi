package domain.piece;

import domain.piece.movementrule.CannonMovementRule;
import domain.piece.movementrule.GeneralMovementRule;
import domain.piece.movementrule.JanggiPieceMovementRule;
import domain.piece.movementrule.NoneMovementRule;
import domain.piece.route.Route;
import domain.piece.route.routeselector.*;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;

import java.util.ArrayList;
import java.util.List;

public enum JanggiPieceType {

    KING(0, List.of(new InsideOnlyPalaceRouteSelector()), new GeneralMovementRule()),
    HORSE(5, List.of(new HorseRouteSelector()), new GeneralMovementRule()),
    ADVISOR(3, List.of(new InsideOnlyPalaceRouteSelector()), new GeneralMovementRule()),
    ELEPHANT(3, List.of(new ElephantRouteSelector()), new GeneralMovementRule()),
    SOLDIER(2, List.of(new SoldierRouteSelector(), new PalaceForwardRouteSelector()), new GeneralMovementRule()),
    CHARIOT(13, List.of(new LinearRouteSelector(), new PalaceLinearRouteSelector()), new GeneralMovementRule()),
    CANNON(7, List.of(new LinearRouteSelector(), new PalaceLinearRouteSelector()), new CannonMovementRule()),
    EMPTY(0, List.of(), new NoneMovementRule());

    private final int score;
    private final List<RouteSelector> routeSelectors;
    private final JanggiPieceMovementRule movementRule;

    JanggiPieceType(
            int score,
            List<RouteSelector> routeSelectors,
            JanggiPieceMovementRule movementRule
    ) {
        this.score = score;
        this.routeSelectors = routeSelectors;
        this.movementRule = movementRule;
    }

    public Route getRoute(
            JanggiSide side,
            JanggiPosition origin,
            JanggiPosition destination
    ) {
        List<Route> route = new ArrayList<>();
        routeSelectors.stream()
                .map(selector -> selector.getRoute(side, origin, destination))
                .filter(selectedRoute -> !selectedRoute.isEmpty())
                .forEach(route::add);

        if (route.isEmpty()) {
            throw new InvalidPathException();
        }
        return route.getFirst();
    }

    public void validateCanMove(JanggiSide side, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        movementRule.checkPieceCanMove(side, hurdlePiece, hurdleCount, targetPiece);
    }

    public int getScore() {
        return score;
    }
}
