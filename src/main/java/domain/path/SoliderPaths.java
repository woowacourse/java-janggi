package domain.path;

import domain.piece.Side;
import domain.vo.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SoliderPaths implements Paths {

    private final List<Path> basePaths;
    private final Map<Position, List<Path>> palaceDiagonalPaths;

    public SoliderPaths(Side side) {
        basePaths = createPaths(side, List.of());
        if (side == Side.CHO) {
            palaceDiagonalPaths = Map.ofEntries(
                Map.entry(Position.of(5, 2), createPaths(side, List.of(Direction.UP_LEFT, Direction.UP_RIGHT))),
                Map.entry(Position.of(4, 3), createPaths(side, List.of(Direction.UP_RIGHT))),
                Map.entry(Position.of(6, 3), createPaths(side, List.of(Direction.UP_LEFT)))
            );
            return;
        }

        palaceDiagonalPaths = Map.ofEntries(
            Map.entry(Position.of(5, 9), createPaths(side, List.of(Direction.DOWN_LEFT, Direction.DOWN_RIGHT))),
            Map.entry(Position.of(4, 8), createPaths(side, List.of(Direction.DOWN_RIGHT))),
            Map.entry(Position.of(6, 8), createPaths(side, List.of(Direction.DOWN_LEFT)))
        );
    }

    private List<Path> createPaths(Side side, List<Direction> directions) {
        List<Path> paths = new ArrayList<>(List.of(
            new Path(List.of(Direction.UP)),
            new Path(List.of(Direction.LEFT)),
            new Path(List.of(Direction.RIGHT))
        ));
        if (side == Side.HAN) {
            paths = new ArrayList<>(List.of(
                new Path(List.of(Direction.DOWN)),
                new Path(List.of(Direction.LEFT)),
                new Path(List.of(Direction.RIGHT))
            ));
        }

        for (Direction direction : directions) {
            paths.add(new Path(List.of(direction)));
        }

        return List.copyOf(paths);
    }

    @Override
    public List<Path> getPaths(Position position) {
        return palaceDiagonalPaths.getOrDefault(position, basePaths);
    }
}
