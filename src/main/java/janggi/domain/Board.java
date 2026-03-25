package janggi.domain;

import janggi.domain.piece.Piece;
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
        positionInfos.stream()
                .forEach(info -> state.put(info.point(), info.piece()));
    }
}
