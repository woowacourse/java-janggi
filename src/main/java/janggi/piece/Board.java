package janggi.piece;

import janggi.Position;
import janggi.piece.strategy.BasicMovable;
import java.util.Map;

public class Board {

    private final Map<BasicMovable, Position> positions;
    private final Map<Position, BasicMovable> pieces;

    public Board(Map<BasicMovable, Position> positions, Map<Position, BasicMovable> pieces) {
        this.positions = positions;
        this.pieces = pieces;
    }


}
