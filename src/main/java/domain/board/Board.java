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

    public Board(Map<Point, Piece> locations) {
        validate(locations);
        this.locations = locations;
    }

    private void validate(Map<Point, Piece> locations) {
        if (locations.size() != VALID_SIZE) {
            throw new JanggiArgumentException("보드의 크기는 9x10 이어야 합니다.");
        }
    }

    public Map<Point, Piece> getLocations() {
        return new HashMap<>(locations);
    }

    public void movePiece(Point originPoint, Point arrivalPoint, Team team) {
        if (locations.containsKey(originPoint) && locations.containsKey(arrivalPoint)) {
            processMovement(originPoint, arrivalPoint, team);
            return;
        }
        throw new JanggiArgumentException("보드의 범위 바깥입니다.");
    }

    private void processMovement(Point originPoint, Point arrivalPoint, Team team) {
        Piece pieceAtOriginPoint = locations.get(originPoint);
        checkOriginPoint(pieceAtOriginPoint, team);

        checkOutOfRoute(originPoint, arrivalPoint, pieceAtOriginPoint);

        List<Point> routePoints = pieceAtOriginPoint.getRoutePoints(originPoint, arrivalPoint);
        PieceOnRoute pieceOnRoute = getAllPieceOnRoute(routePoints);

        checkPieceOnRoute(pieceAtOriginPoint, pieceOnRoute);

        locations.put(arrivalPoint, pieceAtOriginPoint);
        locations.put(originPoint, EMPTY_PIECE);
    }

    private void checkOriginPoint(Piece pieceAtOriginPoint, Team team) {
        if (pieceAtOriginPoint.equals(EMPTY_PIECE)) {
            throw new JanggiArgumentException("출발점에 이동할 기물이 없습니다.");
        }
        if (!pieceAtOriginPoint.hasEqualTeam(team)) {
            throw new JanggiArgumentException("아군 기물만 움직일 수 있습니다.");
        }
    }

    private void checkPieceOnRoute(Piece pieceAtOriginPoint, PieceOnRoute pieceOnRoute) {
        if (!pieceAtOriginPoint.isMovable(pieceOnRoute)) {
            throw new JanggiArgumentException("해당 경로로 이동할 수 없습니다.");
        }
    }

    private void checkOutOfRoute(Point originPoint, Point arrivalPoint, Piece pieceAtOriginPoint) {
        if (!pieceAtOriginPoint.isAbleToArrive(originPoint, arrivalPoint)) {
            throw new JanggiArgumentException("해당 기물이 도착할 수 없는 위치입니다.");
        }
    }


    private PieceOnRoute getAllPieceOnRoute(List<Point> routePoints) {
        return new PieceOnRoute(routePoints.stream()
                .map(locations::get)
                .toList());
    }
}
