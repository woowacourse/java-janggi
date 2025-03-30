package domain.pieces;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.PalaceMovement;
import domain.movements.PieceMovement;
import domain.player.Player;
import domain.player.Score;
import domain.player.Team;
import java.util.List;
import java.util.Objects;

public final class Guard implements Piece {
    private static final PieceDefinition GUARD = PieceDefinition.GUARD;
    private static final Score score = new Score(3.0);

    private final Player player;
    private final PieceMovement movement;

    public Guard(final Player player) {
        this.player = Objects.requireNonNull(player, "Team 정보가 NULL일 수 없습니다.");
        this.movement = new PalaceMovement();
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.player.getTeam().equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        return movement.searchTotalArrivalPoints(start).contains(arrival);
    }

    @Override
    public boolean isMovableOnRoute(final PiecesOnRoute piecesOnRoute) {
        return !piecesOnRoute.hasSameTeamOnArrivalPoint(player.getTeam());
    }

    @Override
    public List<Point> searchRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return GUARD.getNameForTeam(player.getTeam());
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public PieceDefinition getType() {
        return GUARD;
    }

    @Override
    public int getPlayerId() {
        return player.getId();
    }
}
