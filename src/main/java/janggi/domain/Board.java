package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.status.Team;
import janggi.dto.PositionInfo;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Board {

    private static final int BOARD_HEIGHT = 10;

    private final Map<Point, Piece> pieces;

    public Board() {
        this.pieces = new LinkedHashMap<>();
    }

    public void init(List<PositionInfo> positionInfos) {
        positionInfos.forEach(info -> pieces.put(info.point(), info.piece()));
    }

    public void move(Point from, Point to, Team team) {
        validateFromPoint(from, team);
        validateToPoint(to, team);
        Piece piece = pieces.get(from);
        Piece targetPiece = pieces.get(to);
        if (targetPiece != null && !piece.canCapture(targetPiece)) {
            throw new IllegalArgumentException("[ERROR] 이 기물은 해당 타겟을 잡을 수 없습니다.");
        }
        List<Point> route = piece.getRoute(from, to);
        if (!piece.canMove(getPieces(route))) {
            throw new IllegalArgumentException("[ERROR] 해당 기물의 이동 경로에 장애물이 있거나 규칙에 어긋납니다.");
        }
        pieces.remove(from);
        pieces.put(to, piece);
    }

    public boolean isKingDie(Team team) {
        return pieces.values().stream()
                .noneMatch(piece -> piece.isSameType(PieceType.JANG) &&
                        piece.isSameTeam(team));
    }

    public List<List<Piece>> getPoints() {
        List<List<Piece>> pieces = new ArrayList<>();
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            pieces.add(
                    Point.getRow(i).stream()
                            .map(this.pieces::get)
                            .toList()
            );
        }
        return pieces;
    }

    public List<Piece> getPieces(List<Point> point) {
        return point.stream()
                .map(pieces::get)
                .filter(Objects::nonNull)
                .toList();
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
