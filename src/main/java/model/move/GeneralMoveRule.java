package model.move;

import java.util.List;
import model.board.Palace;
import model.policy.DefaultPathPolicy;
import model.policy.PathPolicy;

public class GeneralMoveRule extends MoveRule {

    @Override
    protected List<MovePattern> patterns(Move move) {
        if (!move.isStraight() || !Palace.isPalaceBound(move.to())) {
            return List.of();
        }

        return createPatterns(move);
    }

    private List<MovePattern> createPatterns(Move move) {
        PathPolicy pathPolicy = new DefaultPathPolicy();

        List<Step> steps = List.of(new Step(move.direction()));
        return List.of(new MovePattern(steps, pathPolicy));
    }
}
