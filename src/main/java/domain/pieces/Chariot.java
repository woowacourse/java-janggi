package domain.pieces;

import static domain.pieces.PieceType.CHARIOT;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.PieceMovement;
import domain.movements.StraightLineMovement;
import domain.player.Score;
import domain.player.TeamType;
import java.util.List;

public final class Chariot implements Piece {

    private static final PieceType PIECE_TYPE = CHARIOT;
    private final TeamType teamType;
    private final PieceMovement movement;

    public Chariot(final TeamType teamType) {
        this.teamType = teamType;
        this.movement = new StraightLineMovement();
    }

    @Override
    public boolean hasEqualTeam(final TeamType teamType) {
        return this.teamType.equals(teamType);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        final List<Point> arrivalPoints = movement.calculateTotalArrivalPoints(start);
        return arrivalPoints.contains(arrival);
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        if (piecesOnRoute.hasSameTeamOnArrivalPoint(teamType)) {
            return false;
        }
        return piecesOnRoute.hasNotPieceOnRoute();
    }

    @Override
    public List<Point> getRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return PIECE_TYPE.getNameForTeam(teamType);
    }

    @Override
    public Score getScore() {
        return PIECE_TYPE.getScore();
    }
}
