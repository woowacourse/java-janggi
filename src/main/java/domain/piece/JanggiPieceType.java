package domain.piece;

import static domain.piece.route.JanggiPieceRoute.ADVISOR_ROUTE;
import static domain.piece.route.JanggiPieceRoute.CANNON_ROUTE;
import static domain.piece.route.JanggiPieceRoute.CHARIOT_ROUTE;
import static domain.piece.route.JanggiPieceRoute.ELEPHANT_ROUTE;
import static domain.piece.route.JanggiPieceRoute.EMPTY_ROUTE;
import static domain.piece.route.JanggiPieceRoute.HORSE_ROUTE;
import static domain.piece.route.JanggiPieceRoute.KING_ROUTE;
import static domain.piece.route.JanggiPieceRoute.SOLDIER_ROUTE;

import domain.MovingPattern;
import domain.piece.movementrule.CannonMovementRule;
import domain.piece.movementrule.GeneralMovementRule;
import domain.piece.movementrule.JanggiPieceMovementRule;
import domain.piece.movementrule.NoneMovementStrategy;
import domain.piece.route.JanggiPieceRoute;
import domain.position.JanggiPosition;
import java.util.List;

public enum JanggiPieceType {

    KING(0, KING_ROUTE, new GeneralMovementRule()),
    HORSE(5, HORSE_ROUTE, new GeneralMovementRule()),
    ADVISOR(3, ADVISOR_ROUTE, new GeneralMovementRule()),
    ELEPHANT(3, ELEPHANT_ROUTE, new GeneralMovementRule()),
    SOLDIER(2, SOLDIER_ROUTE, new GeneralMovementRule()),
    CHARIOT(13, CHARIOT_ROUTE, new GeneralMovementRule()),
    CANNON(7, CANNON_ROUTE, new CannonMovementRule()),
    EMPTY(0,EMPTY_ROUTE, new NoneMovementStrategy());

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

    public List<MovingPattern> getRoute(
            JanggiSide side,
            JanggiPosition origin,
            JanggiPosition destination
    ) {
        return route.getRoute(side, origin, destination);
    }

    public void validateCanMove(JanggiSide side, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        movementRule.checkPieceCanMove(side, hurdlePiece, hurdleCount, targetPiece);
    }
}
