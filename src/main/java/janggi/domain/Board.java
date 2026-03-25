package janggi.domain;

import janggi.domain.piece.Piece;
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

    public void move(Point from, Point to) {

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
}
