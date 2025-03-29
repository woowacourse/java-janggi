package janggi.domain.piece;

import janggi.domain.Dynasty;
import janggi.domain.board.Direction;
import janggi.domain.board.Point;
import java.util.List;
import java.util.Set;

public class ChuSoldier extends Soldier {

    private static final Set<List<Direction>> PATHS = Set.of(
            List.of(Direction.UP),
            List.of(Direction.LEFT),
            List.of(Direction.RIGHT)
    );

    public ChuSoldier() {
        super(Dynasty.CHU);
    }

    @Override
    public Path calculatePath(Point start, Point end) {
        return Path.calculatePath(start, end, PATHS);
    }
}
