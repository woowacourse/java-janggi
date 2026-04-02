package model.move;

import java.util.ArrayList;
import java.util.List;

import model.board.Country;
import model.policy.DefaultDestinationPolicy;
import model.policy.DefaultPathPolicy;


public class ElephantMoveRule extends PatternMoveRule {

    public ElephantMoveRule() {
        super(new DefaultPathPolicy(), new DefaultDestinationPolicy());
    }

    @Override
    protected List<MovePattern> patterns(Move move, Country country) {
        List<MovePattern> patterns = new ArrayList<>();

        // 위쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.UP),
                new Step(Direction.UP_LEFT),
                new Step(Direction.UP_LEFT)
        ), pathPolicy(), destinationPolicy()));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.UP),
                new Step(Direction.UP_RIGHT),
                new Step(Direction.UP_RIGHT)
        ), pathPolicy(), destinationPolicy()));

        // 오른쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.RIGHT),
                new Step(Direction.DOWN_RIGHT),
                new Step(Direction.DOWN_RIGHT)
        ), pathPolicy(), destinationPolicy()));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.RIGHT),
                new Step(Direction.UP_RIGHT),
                new Step(Direction.UP_RIGHT)
        ), pathPolicy(), destinationPolicy()));

        // 아래쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.DOWN),
                new Step(Direction.DOWN_RIGHT),
                new Step(Direction.DOWN_RIGHT)
        ), pathPolicy(), destinationPolicy()));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.DOWN),
                new Step(Direction.DOWN_LEFT),
                new Step(Direction.DOWN_LEFT)
        ), pathPolicy(), destinationPolicy()));

        // 왼쪽 방향
        patterns.add(new MovePattern(List.of(
                new Step(Direction.LEFT),
                new Step(Direction.DOWN_LEFT),
                new Step(Direction.DOWN_LEFT)
        ), pathPolicy(), destinationPolicy()));

        patterns.add(new MovePattern(List.of(
                new Step(Direction.LEFT),
                new Step(Direction.UP_LEFT),
                new Step(Direction.UP_LEFT)
        ), pathPolicy(), destinationPolicy()));

        return patterns;
    }
}
