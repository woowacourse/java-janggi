package domain.pieces;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.PieceMovement;
import domain.movements.StraightLineMovement;
import domain.player.Score;
import domain.player.Team;
import java.util.List;

public final class Chariot implements Piece {

    private static final Score score = new Score(13.0);

    private final Team team;
    private final PieceMovement movement;

    public Chariot(final Team team) {
        this.team = team;
        this.movement = new StraightLineMovement();
    }

    public Chariot(Team team, PieceMovement movement) {
        this.team = team;
        this.movement = movement;
    }


    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        final List<Point> arrivalPoints = movement.searchTotalArrivalPoints(start);
        return arrivalPoints.contains(arrival);
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        if (piecesOnRoute.hasSameTeamOnArrivalPoint(team)) {
            return false;
        }
        return piecesOnRoute.hasNotPieceOnRoute();
    }

    @Override
    public List<Point> searchRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return PieceName.CHARIOT.getNameForTeam(team);
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public Piece inRangeOfPalace() {
        return new Chariot(team, StraightLineMovement.generateInRangeOfPalace());
    }
}
