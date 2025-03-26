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

    public void movePiece(
            final Point start,
            final Point arrival,
            final Team team
    ) {
        final Piece piece = getCheckedPieceCanMoveOnStartPoint(start, arrival, team);
        checkPieceCanMoveOnRoute(start, arrival, piece);
        movePieceLocation(start, arrival, piece);
    }

    public Map<Point, Piece> getLocations() {
        return new HashMap<>(locations);
    }

    private Piece getCheckedPieceCanMoveOnStartPoint(final Point start, final Point arrival, final Team team) {
        checkInRangeOnBoard(start, arrival);
        final Piece piece = Optional.ofNullable(locations.get(start))
                .orElseThrow(() -> new JanggiGameRuleWarningException("출발점에 이동할 기물이 없습니다."));
        checkEqualTeam(piece, team);
        checkOutOfRoute(start, arrival, piece);
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
        final boolean isInvalidStartPoint = start.isInRange(BOARD_ROW_MAX, BOARD_COLUMN_MAX);
        final boolean isInvalidArrivalPoint = arrival.isInRange(BOARD_ROW_MAX, BOARD_COLUMN_MAX);
        if (!(isInvalidStartPoint && isInvalidArrivalPoint)) {
            throw new JanggiGameRuleWarningException(
                    "보드의 범위 바깥입니다. 출발점 유효: " + isInvalidStartPoint + ", 도착점 유효: " + isInvalidArrivalPoint);
        }
    }

    private void checkPieceCanMoveOnRoute(
            final Point start,
            final Point arrival,
            final Piece piece
    ) {
        final List<Point> routePoints = piece.getRoutePoints(start, arrival);
        final PiecesOnRoute piecesOnRoute = getAllPiecesOnRoute(routePoints);
        if (!piece.isMovableOnRoute(piecesOnRoute)) {
            throw new JanggiGameRuleWarningException("해당 경로로 이동할 수 없습니다.");
        }
    }

    private void movePieceLocation(final Point start, final Point arrival, final Piece piece) {
        locations.put(arrival, piece);
        locations.remove(start);
    }

    private PiecesOnRoute getAllPiecesOnRoute(final List<Point> pointsOnRoute) {
        return new PiecesOnRoute(pointsOnRoute.stream()
                .map(point -> locations.getOrDefault(point, null))
                .toList());
    }
}
