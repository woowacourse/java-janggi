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

    KING(0, List.of(InsideOnlyPalaceRouteSelector.getInstance()), GeneralMovementRule.getInstance()),
    HORSE(5, List.of(HorseRouteSelector.getInstance()), GeneralMovementRule.getInstance()),
    ADVISOR(3, List.of(InsideOnlyPalaceRouteSelector.getInstance()), GeneralMovementRule.getInstance()),
    ELEPHANT(3, List.of(ElephantRouteSelector.getInstance()), GeneralMovementRule.getInstance()),
    SOLDIER(2, List.of(SoldierRouteSelector.getInstance(), PalaceForwardRouteSelector.getInstance()), GeneralMovementRule.getInstance()),
    CHARIOT(13, List.of(LinearRouteSelector.getInstance(), PalaceLinearRouteSelector.getInstance()), GeneralMovementRule.getInstance()),
    CANNON(7, List.of(LinearRouteSelector.getInstance(), PalaceLinearRouteSelector.getInstance()), CannonMovementRule.getInstance()),
    EMPTY(0, List.of(), NoneMovementRule.getInstance());

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
