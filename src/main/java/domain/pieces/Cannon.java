package domain.pieces;

import static domain.pieces.PieceType.CANNON;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.PieceMovement;
import domain.movements.StraightLineMovement;
import domain.player.Score;
import domain.player.TeamType;
import java.util.List;

public final class Cannon implements Piece {

    private static final int VALID_BETWEEN_PIECE_COUNT = 1;
    private static final PieceType PIECE_TYPE = CANNON;

    private final TeamType teamType;
    private final PieceMovement movement;

    public Cannon(final TeamType teamType) {
        this.teamType = teamType;
        this.movement = new StraightLineMovement();
    }

    public Cannon(TeamType teamType, PieceMovement movement) {
        this.teamType = teamType;
        this.movement = movement;
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
        if (piecesOnRoute.count() != VALID_BETWEEN_PIECE_COUNT) {
            return false;
        }
        if (piecesOnRoute.canNotJumpOverFirstPiece()) {
            return false;
        }
        return !piecesOnRoute.hasSameTeamOnArrivalPoint(teamType);
    }

    @Override
    public boolean canNotJumpOver() {
        return true;
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

    @Override
    public Piece inRangeOfPalace() {
        return new Cannon(teamType, StraightLineMovement.generateInRangeOfPalace());
    }
}
