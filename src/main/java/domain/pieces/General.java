package domain.pieces;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.PalaceMovement;
import domain.movements.PieceMovement;
import domain.player.Score;
import domain.player.TeamType;
import java.util.List;

public final class General implements Piece {

    private static final Score score = new Score(Double.MAX_VALUE);

    private final TeamType teamType;
    private final PieceMovement movement;

    public General(final TeamType teamType) {
        this.teamType = teamType;
        this.movement = new PalaceMovement();
    }

    @Override
    public boolean hasEqualTeam(final TeamType teamType) {
        return this.teamType.equals(teamType);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        return movement.searchTotalArrivalPoints(start).contains(arrival);
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        return !piecesOnRoute.hasSameTeamOnArrivalPoint(teamType);

    }

    @Override
    public List<Point> searchRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return PieceName.GENERAL.getNameForTeam(teamType);
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public boolean canContinueWhenThisRemove() {
        return false;
    }
}
