package model.move;

import java.util.List;
import model.policy.PathPolicy;
import model.policy.SoldierPathPolicy;

public class SoldierMoveRule extends MoveRule {

    @Override
    protected List<MovePattern> patterns(Move move) {
        if (!move.isStraight()) {
            return List.of();
        }

        return createPatterns(move);
    }

    private List<MovePattern> createPatterns(Move move) {
        PathPolicy pathPolicy = new SoldierPathPolicy();

        List<Step> steps = List.of(new Step(move.direction()));
        return List.of(new MovePattern(steps, pathPolicy));
    }
}
