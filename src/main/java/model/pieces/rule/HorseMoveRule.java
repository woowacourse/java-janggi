package model.pieces.rule;

import java.util.ArrayList;
import java.util.List;

public class HorseMoveRule extends MoveRule {
    public HorseMoveRule() {
        super(createPatterns());
    }

    private static List<MovePattern> createPatterns() {
        List<MovePattern> patterns = new ArrayList<>();

        // 위쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.UP, true),
                new Step(Direction.UP_LEFT, false)
        )));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.UP, true),
                new Step(Direction.UP_RIGHT, false)
        )));

        // 오른쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.RIGHT, true),
                new Step(Direction.DOWN_RIGHT, false)
        )));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.RIGHT, true),
                new Step(Direction.UP_RIGHT, false)
        )));

        // 아래쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.DOWN, true),
                new Step(Direction.DOWN_RIGHT, false)
        )));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.DOWN, true),
                new Step(Direction.DOWN_LEFT, false)
        )));

        // 왼쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.LEFT, true),
                new Step(Direction.DOWN_LEFT, false)
        )));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.LEFT, true),
                new Step(Direction.UP_LEFT, false)
        )));

        return patterns;
    }
}
