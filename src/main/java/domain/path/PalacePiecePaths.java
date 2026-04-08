package domain.path;

import domain.movement.Direction;
import domain.vo.Position;
import java.util.List;
import java.util.Map;

public class PalacePiecePaths implements Paths {

    private final List<Path> NORMAL_PATHS = List.of(
        new Path(List.of(Direction.UP)),
        new Path(List.of(Direction.DOWN)),
        new Path(List.of(Direction.RIGHT)),
        new Path(List.of(Direction.LEFT))
    );

    private final List<Path> PALACE_CENTER_PATHS = List.of(
        new Path(List.of(Direction.UP)),
        new Path(List.of(Direction.DOWN)),
        new Path(List.of(Direction.RIGHT)),
        new Path(List.of(Direction.LEFT)),
        new Path(List.of(Direction.UP_LEFT)),
        new Path(List.of(Direction.UP_RIGHT)),
        new Path(List.of(Direction.DOWN_LEFT)),
        new Path(List.of(Direction.DOWN_RIGHT))
    );

    private final Map<Position, List<Path>> PALACE_PATHS = Map.ofEntries(
        // 궁성 중앙
        Map.entry(Position.of(5, 2), PALACE_CENTER_PATHS),
        Map.entry(Position.of(5, 9), PALACE_CENTER_PATHS),

        // 궁성 좌상단 코너
        Map.entry(Position.of(4, 1), List.of(new Path(List.of(Direction.DOWN)), new Path(List.of(Direction.RIGHT)),
            new Path(List.of(Direction.DOWN_RIGHT)))),
        Map.entry(Position.of(4, 8), List.of(new Path(List.of(Direction.DOWN)), new Path(List.of(Direction.RIGHT)),
            new Path(List.of(Direction.DOWN_RIGHT)))),

        // 궁성 우상단 코너
        Map.entry(Position.of(6, 1), List.of(new Path(List.of(Direction.DOWN)), new Path(List.of(Direction.LEFT)),
            new Path(List.of(Direction.DOWN_LEFT)))),
        Map.entry(Position.of(6, 8), List.of(new Path(List.of(Direction.DOWN)), new Path(List.of(Direction.LEFT)),
            new Path(List.of(Direction.DOWN_LEFT)))),

        // 궁성 좌하단 코너
        Map.entry(Position.of(4, 3), List.of(new Path(List.of(Direction.UP)), new Path(List.of(Direction.RIGHT)),
            new Path(List.of(Direction.UP_RIGHT)))),
        Map.entry(Position.of(4, 10), List.of(new Path(List.of(Direction.UP)), new Path(List.of(Direction.RIGHT)),
            new Path(List.of(Direction.UP_RIGHT)))),

        // 궁성 우하단 코너
        Map.entry(Position.of(6, 3), List.of(new Path(List.of(Direction.UP)), new Path(List.of(Direction.LEFT)),
            new Path(List.of(Direction.UP_LEFT)))),
        Map.entry(Position.of(6, 10), List.of(new Path(List.of(Direction.UP)), new Path(List.of(Direction.LEFT)),
            new Path(List.of(Direction.UP_LEFT)))),

        // 궁성 상단 면
        Map.entry(Position.of(5, 1), List.of(new Path(List.of(Direction.DOWN)), new Path(List.of(Direction.RIGHT)),
            new Path(List.of(Direction.LEFT)))),
        Map.entry(Position.of(5, 8), List.of(new Path(List.of(Direction.DOWN)), new Path(List.of(Direction.RIGHT)),
            new Path(List.of(Direction.LEFT)))),

        // 궁성 하단 면
        Map.entry(Position.of(5, 3), List.of(new Path(List.of(Direction.UP)), new Path(List.of(Direction.RIGHT)),
            new Path(List.of(Direction.LEFT)))),
        Map.entry(Position.of(5, 10), List.of(new Path(List.of(Direction.UP)), new Path(List.of(Direction.RIGHT)),
            new Path(List.of(Direction.LEFT)))),

        // 궁성 우측 면
        Map.entry(Position.of(4, 2), List.of(new Path(List.of(Direction.UP)), new Path(List.of(Direction.RIGHT)),
            new Path(List.of(Direction.DOWN)))),
        Map.entry(Position.of(4, 9), List.of(new Path(List.of(Direction.UP)), new Path(List.of(Direction.RIGHT)),
            new Path(List.of(Direction.DOWN)))),

        // 궁성 우측 면
        Map.entry(Position.of(6, 2), List.of(new Path(List.of(Direction.UP)), new Path(List.of(Direction.LEFT)),
            new Path(List.of(Direction.DOWN)))),
        Map.entry(Position.of(6, 9), List.of(new Path(List.of(Direction.UP)), new Path(List.of(Direction.LEFT)),
            new Path(List.of(Direction.DOWN))))
    );

    @Override
    public List<Path> getPaths(Position position) {
        return PALACE_PATHS.getOrDefault(position, NORMAL_PATHS);
    }
}
