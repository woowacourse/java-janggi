package model.move;

import model.policy.*;

import java.util.ArrayList;
import java.util.List;

public class SoldierMoveRule extends MoveRule {

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

        List<Step> steps = List.of(new Step(move.direction()));
        return List.of(new MovePattern(steps, pathPolicy, destinationPolicy));
    }
}
