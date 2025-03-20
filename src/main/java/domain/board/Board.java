package domain.board;

import domain.Team;
import domain.pieces.EmptyPiece;
import domain.pieces.Piece;
import execptions.JanggiArgumentException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Board {

    private static final int VALID_SIZE = 90;
    private static final EmptyPiece EMPTY_PIECE = new EmptyPiece();

    private final Map<Point, Piece> locations;

    public Board(final Map<Point, Piece> locations) {
        validate(locations);
        this.locations = locations;
    }

    private void validate(final Map<Point, Piece> locations) {
        if (locations.size() != VALID_SIZE) {
            throw new JanggiArgumentException("보드의 크기는 9x10 이어야 합니다.");
        }
    }

    public Map<Point, Piece> getLocations() {
        return new HashMap<>(locations);
    }

    public void movePiece(final Point startPoint, final Point arrivalPoint, final Team team) {
        if (locations.containsKey(startPoint) && locations.containsKey(arrivalPoint)) {
            processMovement(startPoint, arrivalPoint, team);
            return;
        }
        throw new JanggiArgumentException("보드의 범위 바깥입니다.");
    }

    private void processMovement(final Point startPoint, final Point arrivalPoint, final Team team) {
        final Piece pieceAtStartPoint = locations.get(startPoint);
        checkStartPoint(pieceAtStartPoint, team);

        checkOutOfRoute(startPoint, arrivalPoint, pieceAtStartPoint);

        final List<Point> routePoints = pieceAtStartPoint.getRoutePoints(startPoint, arrivalPoint);
        final PieceOnRoute pieceOnRoute = getAllPieceOnRoute(routePoints);

        checkPieceOnRoute(pieceAtStartPoint, pieceOnRoute);

        locations.put(arrivalPoint, pieceAtStartPoint);
        locations.put(startPoint, EMPTY_PIECE);
    }

    private void checkStartPoint(final Piece pieceAtStartPoint, final Team team) {
        if (pieceAtStartPoint.equals(EMPTY_PIECE)) {
            throw new JanggiArgumentException("출발점에 이동할 기물이 없습니다.");
        }
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
        return new PieceOnRoute(routePoints.stream()
                .map(locations::get)
                .toList());
    }
}
