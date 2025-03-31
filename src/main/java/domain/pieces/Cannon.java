package domain.pieces;

import domain.board.PiecesOnRoute;
import domain.board.Point;
import domain.movements.PieceMovement;
import domain.movements.StraightLineMovement;
import domain.player.Player;
import domain.player.Score;
import domain.player.Team;
import java.util.List;
import java.util.Objects;

public final class Cannon implements Piece {
    private static final int VALID_BETWEEN_PIECE_COUNT = 1;
    private static final PieceDefinition CANNON = PieceDefinition.CANNON;
    private static final Score score = new Score(2.0);

    private final Player player;
    private final PieceMovement movement;

    public Cannon(final Player player) {
        this.player = Objects.requireNonNull(player, "Team 정보가 NULL일 수 없습니다.");
        this.movement = new StraightLineMovement();
    }

    public Cannon(final Player player, final PieceMovement movement) {
        this.player = player;
        this.movement = movement;
    }

    @Override
    public boolean hasEqualTeam(final Team team) {
        return this.player.getTeam().equals(team);
    }

    @Override
    public boolean isAbleToArrive(final Point start, final Point arrival) {
        final List<Point> arrivalPoints = movement.searchTotalArrivalPoints(start);
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
        return !piecesOnRoute.hasSameTeamOnArrivalPoint(player.getTeam());
    }

    @Override
    public boolean canNotJumpOver() {
        return true;
    }

    @Override
    public List<Point> searchRoutePoints(final Point start, final Point arrival) {
        return movement.calculatePointsOnRoute(start, arrival);
    }

    @Override
    public String getName() {
        return CANNON.getNameForTeam(player.getTeam());
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public PieceDefinition getType() {
        return CANNON;
    }

    @Override
    public int getPlayerId() {
        return player.getId();
    }

    @Override
    public Piece inRangeOfPalace() {
        return new Cannon(player, StraightLineMovement.generateInRangeOfPalace());
    }
}
