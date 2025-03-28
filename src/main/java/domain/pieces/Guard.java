package domain.pieces;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.PalaceMovement;
import domain.movements.PieceMovement;
import domain.player.Score;
import domain.player.TeamType;
import java.util.List;

public final class Guard implements Piece {

    private static final Score score = new Score(3.0);

    private final TeamType teamType;
    private final PieceMovement movement;

    public Guard(final TeamType teamType) {
        this.teamType = teamType;
        this.movement = new PalaceMovement();
    }

    @Override
    public boolean hasEqualTeam(final TeamType teamType) {
        return this.teamType.equals(teamType);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        return movement.calculateTotalArrivalPoints(start).contains(arrival);
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        return !piecesOnRoute.hasSameTeamOnArrivalPoint(teamType);
    }

    @Override
    public List<Point> getRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return PieceName.GUARD.getNameForTeam(teamType);
    }

    @Override
    public Score getScore() {
        return score;
    }
}
