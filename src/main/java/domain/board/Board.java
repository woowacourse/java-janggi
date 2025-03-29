package domain.board;

import domain.pieces.Piece;
import domain.player.Score;
import domain.player.Team;
import exceptions.JanggiGameRuleWarningException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class Board {
    private static final int BOARD_ROW_MAX = 10;
    private static final int BOARD_COLUMN_MAX = 9;

    private final Map<Point, Piece> locations;

    public Board(final Map<Point, Piece> locations) {
        final Map<Point, Piece> locationsNotNull = Objects.requireNonNull(locations,
                "위치 정보가 NULL일 수 없습니다.");
        this.locations = new HashMap<>(locationsNotNull);
    }

    public boolean canMovePiece(
            final Point start,
            final Point arrival,
            final Team team
    ) {
        final Piece piece = retrievePieceCanMoveOnStartPoint(start, arrival, team);
        checkPieceCanMoveOnRoute(start, arrival, piece);

        final Piece pieceAtArrival = locations.get(arrival);
        return canContinueWhenPieceRemove(pieceAtArrival);
    }

    public Score movePieceOnLocations(final Point start, final Point arrival) {
        final Piece pieceAtStart = locations.remove(start);
        final Piece pieceAtArrival = locations.remove(arrival);
        final Score score = Optional.ofNullable(pieceAtArrival)
                .map(Piece::getScore)
                .orElseGet(() -> new Score(0.0));
        locations.put(arrival, pieceAtStart);
        return score;
    }

    public Map<Point, Piece> getLocations() {
        return new HashMap<>(locations);
    }

    private Piece retrievePieceCanMoveOnStartPoint(final Point start, final Point arrival, final Team team) {
        checkInRangeOnBoard(start, arrival);
        final Piece piece = Optional.ofNullable(locations.get(start))
                .orElseThrow(() -> new JanggiGameRuleWarningException("출발점에 이동할 기물이 없습니다."));
        checkEqualTeam(piece, team);
        return piece;
    }

    private void checkEqualTeam(final Piece piece, final Team team) {
        if (!piece.hasEqualTeam(team)) {
            throw new JanggiGameRuleWarningException("아군 기물만 움직일 수 있습니다.");
        }
    }

    private void checkOutOfRoute(
            final Point start,
            final Point arrival,
            final Piece pieceAtStart
    ) {
        if (!pieceAtStart.isAbleToArrive(start, arrival)) {
            throw new JanggiGameRuleWarningException("해당 기물이 도착할 수 없는 위치입니다.");
        }
    }

    private void checkInRangeOnBoard(final Point start, final Point arrival) {
        final boolean isStartPointInRange = start.isInRange(BOARD_ROW_MAX, BOARD_COLUMN_MAX);
        final boolean isArrivalPointInRange = arrival.isInRange(BOARD_ROW_MAX, BOARD_COLUMN_MAX);
        if (!(isStartPointInRange && isArrivalPointInRange)) {
            throw new JanggiGameRuleWarningException(
                    "보드의 범위 바깥입니다. 출발점 유효: " + isStartPointInRange + ", 도착점 유효: " + isArrivalPointInRange);
        }
    }

    private void checkPieceCanMoveOnRoute(
            final Point start,
            final Point arrival,
            final Piece originalPiece
    ) {
        Piece movingPiece = originalPiece;
        if (Palace.isInRange(start, arrival)) {
            movingPiece = movingPiece.inRangeOfPalace();
        }
        checkOutOfRoute(start, arrival, movingPiece);

        final List<Point> routePoints = movingPiece.searchRoutePoints(start, arrival);
        final PiecesOnRoute piecesOnRoute = piecesFromRoutePoints(routePoints);
        if (!movingPiece.isMovableOnRoute(piecesOnRoute)) {
            throw new JanggiGameRuleWarningException("해당 경로로 이동할 수 없습니다.");
        }
    }

    private boolean canContinueWhenPieceRemove(final Piece pieceAtArrival) {
        return Optional.ofNullable(pieceAtArrival)
                .map(Piece::canContinueGameAfterRemoval)
                .orElse(true);
    }

    private PiecesOnRoute piecesFromRoutePoints(final List<Point> pointsOnRoute) {
        return new PiecesOnRoute(pointsOnRoute.stream()
                .map(point -> locations.getOrDefault(point, null))
                .toList());
    }
}
