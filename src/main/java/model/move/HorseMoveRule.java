package model.move;

import java.util.ArrayList;
import java.util.List;
import model.policy.DefaultPathPolicy;
import model.policy.PathPolicy;

public class HorseMoveRule extends MoveRule {
    private static final List<Direction> straightDirections = List.of(Direction.UP, Direction.RIGHT, Direction.DOWN,
            Direction.LEFT);
    private static final List<List<Direction>> diagonals = List.of(
            List.of(Direction.UP_LEFT, Direction.UP_RIGHT),
            List.of(Direction.UP_RIGHT, Direction.DOWN_RIGHT),
            List.of(Direction.DOWN_LEFT, Direction.DOWN_RIGHT),
            List.of(Direction.UP_LEFT, Direction.DOWN_LEFT));

    @Override
    protected List<MovePattern> patterns(Move move) {
        List<MovePattern> patternList = new ArrayList<>();
        PathPolicy pathPolicy = new DefaultPathPolicy();

        for (int i = 0; i < straightDirections.size(); i++) {
            Direction base = straightDirections.get(i);
            diagonals.get(i).stream().map(diagonal -> createPattern(base, diagonal, pathPolicy))
                    .forEach(patternList::add);
        }
        return patternList;
    }

    private MovePattern createPattern(
            Direction base,
            Direction diagonal,
            PathPolicy pathPolicy
    ) {
        return new MovePattern(
                List.of(
                        new Step(base),
                        new Step(diagonal)
                ),
                pathPolicy
        );
    }
}
