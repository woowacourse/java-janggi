package janggi.domain.board;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.point.Route;
import janggi.domain.status.Team;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class Board {

    private final Map<Point, Piece> pieces;

    public Board(Map<Point, Piece> pieces) {
        this.pieces = pieces;
    }

    public void move(Point from, Point to, Team team) {
        validateFromPoint(from, team);
        validateToPoint(to, team);
        Piece piece = pieces.get(from);
        Piece targetPiece = pieces.get(to);
        if (targetPiece != null && !piece.canCapture(targetPiece)) {
            throw new IllegalArgumentException("[ERROR] 이 기물은 해당 타겟을 잡을 수 없습니다.");
        }
        Points points = piece.getRoutePoints(from, to);
        if (!piece.canMove(getRoute(points))) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 경로에 장애물이 있거나 규칙에 어긋납니다.");
        }
        pieces.remove(from);
        pieces.put(to, piece);
    }

    public boolean isKingDie() {
        return pieces.values().stream()
                .filter(piece -> piece.isSameType(PieceType.JANG))
                .count() < 2;
    }

    public Map<Point, Piece> getPieces() {
        return Collections.unmodifiableMap(this.pieces);
    }

    public double calculateScore(Team team) {
        return pieces.values()
                .stream()
                .filter(piece -> piece.isSameTeam(team))
                .mapToInt(Piece::getScore)
                .sum();
    }

    public Route getRoute(Points points) {
        return new Route(points.getPoints().stream()
                .map(pieces::get)
                .filter(Objects::nonNull)
                .toList());
    }

    private void validateFromPoint(Point from, Team team) {
        if (isEmptyPoint(from)) {
            throw new IllegalArgumentException("[ERROR] 출발지에 이동할 기물이 없습니다.");
        }
        if (!isSameTeam(from, team)) {
            throw new IllegalArgumentException("[ERROR] 상대방의 기물은 움직일 수 없습니다.");
        }
    }

    private void validateToPoint(Point to, Team team) {
        if (!isEmptyPoint(to) && isSameTeam(to, team)) {
            throw new IllegalArgumentException("[ERROR] 도착지에 본인의 기물이 있습니다.");
        }
    }

    private boolean isSameTeam(Point point, Team team) {
        return pieces.get(point).isSameTeam(team);
    }

    private boolean isEmptyPoint(Point point) {
        return pieces.get(point) == null;
    }
}
