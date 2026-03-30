package janggi.domain.piece.unit;

import janggi.domain.board.path.FixedPathStrategy;
import janggi.domain.board.path.Path;
import janggi.domain.board.path.PathStrategy;
import janggi.domain.board.point.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Pattern;
import janggi.domain.piece.PieceName;
import janggi.domain.side.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Soldier extends Piece {
    private static final PieceName NAME = PieceName.SOLDIER;
    private static final PathStrategy DEFAULT_STRATEGY = new FixedPathStrategy();

    public Soldier(Side side) {
        super(NAME, side, DEFAULT_STRATEGY);
    }

    @Override
    public List<Pattern> patterns() {
        List<Pattern> directions = new ArrayList<>();
        if (Side.CHO.equals(side)) {
            directions.add(new Pattern(List.of(Direction.NORTH)));
            directions.add(new Pattern(List.of(Direction.WEST)));
            directions.add(new Pattern(List.of(Direction.EAST)));
        }
        if (Side.HAN.equals(side)) {
            directions.add(new Pattern(List.of(Direction.SOUTH)));
            directions.add(new Pattern(List.of(Direction.WEST)));
            directions.add(new Pattern(List.of(Direction.EAST)));
        }
        return directions;
    }

    @Override
    protected Path refinePath(Path path, Map<Point, Piece> piecesOnPaths) {
        return path;
    }
}
