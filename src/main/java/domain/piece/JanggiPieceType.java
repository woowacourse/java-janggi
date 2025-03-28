package domain.piece;

import domain.piece.movementrule.CannonMovementRule;
import domain.piece.movementrule.GeneralMovementRule;
import domain.piece.movementrule.JanggiPieceMovementRule;
import domain.piece.movementrule.NoneMovementStrategy;
import domain.piece.route.JanggiPieceRoute;
import domain.piece.route.Route;
import domain.position.JanggiPosition;

import static domain.piece.route.JanggiPieceRoute.*;

public enum JanggiPieceType {

    KING(0, KING_ROUTE, new GeneralMovementRule()),
    HORSE(5, HORSE_ROUTE, new GeneralMovementRule()),
    ADVISOR(3, ADVISOR_ROUTE, new GeneralMovementRule()),
    ELEPHANT(3, ELEPHANT_ROUTE, new GeneralMovementRule()),
    SOLDIER(2, SOLDIER_ROUTE, new GeneralMovementRule()),
    CHARIOT(13, CHARIOT_ROUTE, new GeneralMovementRule()),
    CANNON(7, CANNON_ROUTE, new CannonMovementRule()),
    EMPTY(0, EMPTY_ROUTE, new NoneMovementStrategy());

    private final int score;
    private final JanggiPieceRoute route;
    private final JanggiPieceMovementRule movementRule;

    JanggiPieceType(
            int score,
            JanggiPieceRoute route,
            JanggiPieceMovementRule movementRule
    ) {
        this.score = score;
        this.route = route;
        this.movementRule = movementRule;
    }

    public Route getRoute(
            JanggiSide side,
            JanggiPosition origin,
            JanggiPosition destination
    ) {
        return route.getRoute(side, origin, destination);
    }

    public void validateCanMove(JanggiSide side, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        movementRule.checkPieceCanMove(side, hurdlePiece, hurdleCount, targetPiece);
    }

    public int getScore() {
        return score;
    }
}
