package model.move;

import java.util.List;

import model.board.Country;
import model.board.Palace;
import model.policy.DefaultDestinationPolicy;
import model.policy.DefaultPathPolicy;

public abstract class PalaceOneStepMoveRule extends PatternMoveRule {

    protected PalaceOneStepMoveRule() {
        super(new DefaultPathPolicy(), new DefaultDestinationPolicy());
    }

    @Override
    protected List<MovePattern> patterns(Move move, Country country) {
        if (!isInsidePalace(move, country)) {
            return List.of();
        }

        if (!isMovable(move, country)) {
            return List.of();
        }

        return createPatterns(move);
    }

    private boolean isInsidePalace(Move move, Country country) {
        Palace palace = Palace.from(country);
        return palace.contains(move.from()) && palace.contains(move.to());
    }

    private boolean isMovable(Move move, Country country) {
        if (isStraightOneStep(move)) {
            return true;
        }

        Palace palace = Palace.from(country);
        return palace.isDiagonalMove(move);
    }

    private boolean isStraightOneStep(Move move) {
        if (!move.isStraight()) {
            return false;
        }
        return move.distance() == 1;
    }

    private List<MovePattern> createPatterns(Move move) {
        List<Step> steps = List.of(new Step(move.direction()));
        return List.of(new MovePattern(steps, pathPolicy(), destinationPolicy()));
    }
}