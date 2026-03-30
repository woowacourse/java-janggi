package model.move;

import java.util.ArrayList;
import java.util.List;
import model.policy.CannonPathPolicy;
import model.policy.PathPolicy;

public class CannonMoveRule extends MoveRule {

    @Override
    protected List<MovePattern> patterns(Move move) {
        if (!move.isStraight()) {
            return List.of();
        }

        return createPatterns(move);
    }

    private List<MovePattern> createPatterns(Move move) {
        PathPolicy pathPolicy = new CannonPathPolicy();

        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < move.distance(); i++) {
            steps.add(new Step(move.direction()));
        }

        return List.of(new MovePattern(steps, pathPolicy));
    }
}
