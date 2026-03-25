package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.PieceType;
import janggi.domain.status.Team;
import janggi.dto.PositionInfo;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Board {

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
        state.remove(from);
        state.put(to, piece);
    }

    public boolean isEmptyPoint(Point point) {
        return state.get(point) == null;
    }

    public boolean isSameTeam(Point from, Team team) {
        return state.get(from).isSameTeam(team);
    }

    public Piece getPointAt(Point point) {
        return state.get(point);
    }

    public boolean isKingDie(Point to, Team team) {
        Piece piece = state.get(to);
        return piece.isSameType(PieceType.JANG) && piece.isSameTeam(team);
    }

    private void validateToPoint(Point to, Team team) {
        if (!isEmptyPoint(to) && isSameTeam(to, team)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateFromPoint(Point from, Team team) {
        Piece piece = state.get(from);
        if (isEmptyPoint(from) || !piece.isSameTeam(team)) {
            throw new IllegalArgumentException();
        }
    }
}
