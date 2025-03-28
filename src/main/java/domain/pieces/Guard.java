package domain.pieces;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.PalaceMovement;
import domain.movements.PieceMovement;
import domain.player.Score;
import domain.player.Team;
import java.util.List;

public final class Guard implements Piece {

    private static final Score score = new Score(3.0);

    private final Team team;
    private final PieceMovement movement;

    public Guard(final Team team) {
        this.team = team;
        this.movement = new PalaceMovement();
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        return movement.searchTotalArrivalPoints(start).contains(arrival);
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        return !piecesOnRoute.hasSameTeamOnArrivalPoint(team);
    }

    @Override
    public List<Point> searchRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return PieceName.GUARD.getNameForTeam(team);
    }

    @Override
    public Score getScore() {
        return score;
    }
}
