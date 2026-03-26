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

    private static final int Y = 10;

    private final Map<Point, Piece> state;

    public Board() {
        this.state = new LinkedHashMap<>();
    }

    public void init(List<PositionInfo> positionInfos) {
        positionInfos.forEach(info -> state.put(info.point(), info.piece()));
    }

    public void move(Point from, Point to, Team team) {
        validateFromPoint(from, team);
        validateToPoint(to, team);
        Piece piece = state.get(from);
        List<Point> route = piece.getRoute(from, to);
        if (!piece.canMove(getPieces(route))) {
            throw new IllegalArgumentException("해당 기물의 이동 경로에 장애물이 있거나 규칙에 어긋납니다.");
        }
        state.remove(from);
        state.put(to, piece);
    }

    public boolean isKingDie(Team team) {
        return state.values().stream()
                .noneMatch(piece -> piece.isSameType(PieceType.JANG) &&
                        piece.isSameTeam(team));
    }

    public List<List<Piece>> getPoints() {
        List<List<Piece>> pieces = new ArrayList<>();
        for (int i = 0; i < Y; i++) {
            pieces.add(
                    Point.getRow(i).stream()
                            .map(state::get)
                            .toList()
            );
        }
        return pieces;
    }

    public List<Piece> getPieces(List<Point> point) {
        return point.stream()
                .map(state::get)
                .filter(Objects::nonNull)
                .toList();
    }

    private void validateFromPoint(Point from, Team team) {
        if (isEmptyPoint(from)) {
            throw new IllegalArgumentException("출발지에 이동할 기물이 없습니다.");
        }
        if (!isSameTeam(from, team)) {
            throw new IllegalArgumentException("상대방의 기물은 움직일 수 없습니다.");
        }
    }

    private void validateToPoint(Point to, Team team) {
        if (!isEmptyPoint(to) && isSameTeam(to, team)) {
            throw new IllegalArgumentException("도착지에 본인의 기물이 있습니다.");
        }
    }

    private boolean isSameTeam(Point point, Team team) {
        return state.get(point).isSameTeam(team);
    }

    private boolean isEmptyPoint(Point point) {
        return state.get(point) == null;
    }
}
