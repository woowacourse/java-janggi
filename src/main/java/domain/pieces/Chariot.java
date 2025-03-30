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

public final class Chariot implements Piece {
    private static final PieceDefinition CHARIOT = PieceDefinition.CHARIOT;
    private static final Score score = new Score(13.0);

    private final Player player;
    private final PieceMovement movement;

    public Chariot(final Player player) {
        this.player = Objects.requireNonNull(player, "Team 정보가 NULL일 수 없습니다.");
        this.movement = new StraightLineMovement();
    }

    public Chariot(final Player player, final PieceMovement movement) {
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
        if (piecesOnRoute.hasSameTeamOnArrivalPoint(player.getTeam())) {
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
        return CHARIOT.getNameForTeam(player.getTeam());
    }

    @Override
    public Score getScore() {
        return score;
    }

    @Override
    public PieceDefinition getType() {
        return CHARIOT;
    }

    @Override
    public int getPlayerId() {
        return player.getId();
    }

    @Override
    public Piece inRangeOfPalace() {
        return new Chariot(player, StraightLineMovement.generateInRangeOfPalace());
    }
}
