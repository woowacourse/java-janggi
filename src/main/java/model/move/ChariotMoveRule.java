package model.move;

import java.util.ArrayList;
import java.util.List;
import model.policy.DefaultDestinationPolicy;
import model.policy.DefaultPathPolicy;
import model.policy.DestinationPolicy;
import model.policy.PathPolicy;

public class ChariotMoveRule extends MoveRule {

    @Override
    protected List<MovePattern> patterns(Move move) {
        if (!move.isStraight()) {
            return List.of();
        }

        return createPatterns(move);
    }

    private List<MovePattern> createPatterns(Move move) {
        PathPolicy pathPolicy = new DefaultPathPolicy();
        DestinationPolicy destinationPolicy = new DefaultDestinationPolicy();

        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < move.distance(); i++) {
            steps.add(new Step(move.direction()));
        }

        return List.of(new MovePattern(steps, pathPolicy, destinationPolicy));
    }
}
