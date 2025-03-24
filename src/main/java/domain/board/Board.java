package domain.board;

import domain.Team;
import domain.pieces.Piece;
import execptions.JanggiGameRuleWarningException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Board {

    private static final int BOARD_ROW_MAX = 10;
    private static final int BOARD_COLUMN_MAX = 9;

    private final Map<Point, Piece> locations;

    public Board(final Map<Point, Piece> locations) {
        this.locations = new HashMap<>(locations);
    }

    public Map<Point, Piece> getLocations() {
        return new HashMap<>(locations);
    }

    public void movePiece(
            final Point start,
            final Point arrival,
            final Team team
    ) {
        boolean isInvalidStartPoint = start.isInRange(BOARD_ROW_MAX, BOARD_COLUMN_MAX);
        boolean isInvalidArrivalPoint = arrival.isInRange(BOARD_ROW_MAX, BOARD_COLUMN_MAX);
        if (isInvalidStartPoint && isInvalidArrivalPoint) {
            processMovement(start, arrival, team);
            return;
        }
        throw new JanggiGameRuleWarningException(
                "보드의 범위 바깥입니다. 출발점 유효: " + isInvalidStartPoint + ", 도착점 유효: " + isInvalidArrivalPoint);
    }

    private void processMovement(
            final Point start,
            final Point arrival,
            final Team team
    ) {
        final Piece pieceAtStart = Optional.ofNullable(locations.get(start))
                .orElseThrow(() -> new JanggiGameRuleWarningException("출발점에 이동할 기물이 없습니다."));
        checkStartPoint(pieceAtStart, team);

        checkOutOfRoute(start, arrival, pieceAtStart);

        final List<Point> routePoints = pieceAtStart.getRoutePoints(start, arrival);
        final PiecesOnRoute piecesOnRoute = getAllPieceOnRoute(routePoints);

        checkPieceOnRoute(pieceAtStart, piecesOnRoute);

        locations.put(arrival, pieceAtStart);
        locations.remove(start);
    }

    private void checkStartPoint(final Piece pieceAtStart, final Team team) {
        if (!pieceAtStart.hasEqualTeam(team)) {
            throw new JanggiGameRuleWarningException("아군 기물만 움직일 수 있습니다.");
        }
    }

    private void checkPieceOnRoute(final Piece pieceAtStart, final PiecesOnRoute piecesOnRoute) {
        if (!pieceAtStart.isMovable(piecesOnRoute)) {
            throw new JanggiGameRuleWarningException("해당 경로로 이동할 수 없습니다.");
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

    private PiecesOnRoute getAllPieceOnRoute(final List<Point> routePoints) {
        return new PiecesOnRoute(routePoints.stream()
                .map(point -> locations.getOrDefault(point, null))
                .toList());
    }
}
