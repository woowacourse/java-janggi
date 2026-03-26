package janggi.domain.piece.unit;

import janggi.domain.board.coordinate.FixedPathStrategy;
import janggi.domain.board.coordinate.Path;
import janggi.domain.board.coordinate.PathStrategy;
import janggi.domain.board.coordinate.Point;
import janggi.domain.piece.Direction;
import janggi.domain.piece.Directions;
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
    public List<Point> availablePoints(List<Path> paths, Map<Point, Piece> piecesOnPaths) {
        return List.of();
    }

    @Override
    public List<Directions> directions() {
        List<Directions> directions = new ArrayList<>();
        if (Side.HAN.equals(side)) {
            directions.add(new Directions(List.of(Direction.NORTH),pathStrategy));
            directions.add(new Directions(List.of(Direction.WEST), pathStrategy));
            directions.add(new Directions(List.of(Direction.EAST), pathStrategy));
        }
        if (Side.CHO.equals(side)) {
            directions.add(new Directions(List.of(Direction.SOUTH), pathStrategy));
            directions.add(new Directions(List.of(Direction.WEST), pathStrategy));
            directions.add(new Directions(List.of(Direction.EAST), pathStrategy));
        }
        return directions;
    }

    @Override
    protected Path cutPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return path;
    }

    @Override
    protected boolean isValidPath(Path path, Map<Point, Piece> piecesOnPaths) {
        return true;
    }
}
