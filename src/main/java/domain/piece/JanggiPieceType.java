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
import domain.piece.movingstrategy.MovingNormalStrategy;
import domain.piece.movingstrategy.JanggiPieceMovingStrategy;
import domain.piece.movingstrategy.MovingCannonStrategy;
import domain.piece.movingstrategy.NoneMovingStrategy;
import domain.piece.route.JanggiPieceRoute;
import domain.position.JanggiPosition;
import java.util.List;

public enum JanggiPieceType {

    KING(0, KING_ROUTE, new MovingNormalStrategy()),
    HORSE(5, HORSE_ROUTE, new MovingNormalStrategy()),
    ADVISOR(3, ADVISOR_ROUTE, new MovingNormalStrategy()),
    ELEPHANT(3, ELEPHANT_ROUTE, new MovingNormalStrategy()),
    SOLDIER(2, SOLDIER_ROUTE, new MovingNormalStrategy()),
    CHARIOT(13, CHARIOT_ROUTE, new MovingNormalStrategy()),
    CANNON(7, CANNON_ROUTE, new MovingCannonStrategy()),
    EMPTY(0,EMPTY_ROUTE, new NoneMovingStrategy());

    private final int score;
    private final JanggiPieceRoute route;
    private final JanggiPieceMovingStrategy movingStrategy;

    JanggiPieceType(
            int score,
            JanggiPieceRoute route,
            JanggiPieceMovingStrategy movingStrategy
    ) {
        this.score = score;
        this.route = route;
        this.movingStrategy = movingStrategy;
    }

    public List<MovingPattern> getRoute(
            JanggiSide side,
            JanggiPosition origin,
            JanggiPosition destination
    ) {
        return route.getRoute(side, origin, destination);
    }

    public void validateCanMove(JanggiSide side, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        movingStrategy.checkPieceCanMove(side, hurdlePiece, hurdleCount, targetPiece);
    }
}
