package domain.board;

import domain.Team;
import domain.pieces.Piece;
import execptions.JanggiArgumentException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Board {

    private static final int VALID_SIZE = 90;
    private static final int VALID_ROW_SIZE = 10;
    private static final int VALID_COLUMN_SIZE = 9;

    private final Map<Point, Piece> locations;

    public Board(final Map<Point, Piece> locations) {
        validate(locations);
        this.locations = locations;
    }

    private void validate(final Map<Point, Piece> locations) {
        validateRange(locations);
    }

    private static void validateRange(Map<Point, Piece> locations) {
        for (Point point : locations.keySet()) {
            if (point.row() >= VALID_ROW_SIZE || point.column() >= VALID_COLUMN_SIZE) {
                throw new JanggiArgumentException("보드의 크기 범위에 맞지 않습니다.");
            }
        }
    }

    public Map<Point, Piece> getLocations() {
        return new HashMap<>(locations);
    }

    public void movePiece(final Point startPoint, final Point arrivalPoint, final Team team) {
        processMovement(startPoint, arrivalPoint, team);
    }

    private void processMovement(final Point startPoint, final Point arrivalPoint, final Team team) {
        final Piece pieceAtStartPoint = locations.get(startPoint);
        checkStartPoint(startPoint, team);

        checkOutOfRoute(startPoint, arrivalPoint, pieceAtStartPoint);

        final List<Point> routePoints = pieceAtStartPoint.getRoutePoints(startPoint, arrivalPoint);
        final PieceOnRoute pieceOnRoute = getAllPieceOnRoute(routePoints);

        checkPieceOnRoute(pieceAtStartPoint, pieceOnRoute);

        locations.put(arrivalPoint, pieceAtStartPoint);
        locations.remove(startPoint);
    }

    private void checkStartPoint(final Point startPoint, final Team team) {
        if (!locations.containsKey(startPoint)) {
            throw new JanggiArgumentException("출발점에 이동할 기물이 없습니다.");
        }

        Piece pieceAtStartPoint = locations.get(startPoint);

        if (!pieceAtStartPoint.hasEqualTeam(team)) {
            throw new JanggiArgumentException("아군 기물만 움직일 수 있습니다.");
        }
    }

    private void checkPieceOnRoute(final Piece pieceAtStartPoint, final PieceOnRoute pieceOnRoute) {
        if (!pieceAtStartPoint.isMovable(pieceOnRoute)) {
            throw new JanggiArgumentException("해당 경로로 이동할 수 없습니다.");
        }
    }

    private void checkOutOfRoute(final Point startPoint, final Point arrivalPoint, final Piece pieceAtStartPoint) {
        if (!pieceAtStartPoint.isAbleToArrive(startPoint, arrivalPoint)) {
            throw new JanggiArgumentException("해당 기물이 도착할 수 없는 위치입니다.");
        }
    }

    private PieceOnRoute getAllPieceOnRoute(final List<Point> routePoints) {
        Piece pieceAtArrivalPoint = locations.getOrDefault(routePoints.getLast(), null);

        List<Piece> piecesOnRoute = routePoints.subList(0, routePoints.size() - 1).stream()
                .filter(locations::containsKey)
                .map(locations::get)
                .toList();

        return new PieceOnRoute(piecesOnRoute, pieceAtArrivalPoint);
    }
}
