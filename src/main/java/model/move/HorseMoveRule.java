package model.move;

import java.util.ArrayList;
import java.util.List;
import model.policy.DefaultDestinationPolicy;
import model.policy.DefaultPathPolicy;
import model.policy.DestinationPolicy;
import model.policy.PathPolicy;

public class HorseMoveRule extends MoveRule {

    @Override
    protected List<MovePattern> patterns(Move move) {
        List<MovePattern> patterns = new ArrayList<>();
        PathPolicy pathPolicy = new DefaultPathPolicy();
        DestinationPolicy destinationPolicy = new DefaultDestinationPolicy();

        // 위쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.UP),
                new Step(Direction.UP_LEFT)
        ), pathPolicy, destinationPolicy));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.UP),
                new Step(Direction.UP_RIGHT)
        ), pathPolicy, destinationPolicy));

        // 오른쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.RIGHT),
                new Step(Direction.DOWN_RIGHT)
        ), pathPolicy, destinationPolicy));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.RIGHT),
                new Step(Direction.UP_RIGHT)
        ), pathPolicy, destinationPolicy));

        // 아래쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.DOWN),
                new Step(Direction.DOWN_RIGHT)
        ), pathPolicy, destinationPolicy));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.DOWN),
                new Step(Direction.DOWN_LEFT)
        ), pathPolicy, destinationPolicy));

        // 왼쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.LEFT),
                new Step(Direction.DOWN_LEFT)
        ), pathPolicy, destinationPolicy));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.LEFT),
                new Step(Direction.UP_LEFT)
        ), pathPolicy, destinationPolicy));

        return patterns;
    }
}
