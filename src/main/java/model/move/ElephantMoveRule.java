package model.move;

import model.policy.DefaultDestinationPolicy;
import model.policy.DefaultPathPolicy;
import model.policy.DestinationPolicy;
import model.policy.PathPolicy;

import java.util.ArrayList;
import java.util.List;

public class ElephantMoveRule extends MoveRule{
    public ElephantMoveRule() {
        super(createPatterns());
    }

    private static List<MovePattern> createPatterns() {
        List<MovePattern> patterns = new ArrayList<>();
        PathPolicy pathPolicy = new DefaultPathPolicy();
        DestinationPolicy destinationPolicy = new DefaultDestinationPolicy();

        // 위쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.UP, true),
                new Step(Direction.UP_LEFT, true),
                new Step(Direction.UP_LEFT, false)
        ), pathPolicy, destinationPolicy));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.UP, true),
                new Step(Direction.UP_RIGHT, true),
                new Step(Direction.UP_RIGHT, false)
        ), pathPolicy, destinationPolicy));

        // 오른쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.RIGHT, true),
                new Step(Direction.DOWN_RIGHT, true),
                new Step(Direction.DOWN_RIGHT, false)
        ), pathPolicy, destinationPolicy));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.RIGHT, true),
                new Step(Direction.UP_RIGHT, true),
                new Step(Direction.UP_RIGHT, false)
        ), pathPolicy, destinationPolicy));

        // 아래쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.DOWN, true),
                new Step(Direction.DOWN_RIGHT, true),
                new Step(Direction.DOWN_RIGHT, false)
        ), pathPolicy, destinationPolicy));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.DOWN, true),
                new Step(Direction.DOWN_LEFT, true),
                new Step(Direction.DOWN_LEFT, false)
        ), pathPolicy, destinationPolicy));

        // 왼쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.LEFT, true),
                new Step(Direction.DOWN_LEFT, true),
                new Step(Direction.DOWN_LEFT, false)
        ), pathPolicy, destinationPolicy));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.LEFT, true),
                new Step(Direction.UP_LEFT, true),
                new Step(Direction.UP_LEFT, false)
        ), pathPolicy, destinationPolicy));

        return patterns;
    }
}
