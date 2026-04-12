package model.move;

import java.util.ArrayList;
import java.util.List;

import model.board.Country;
import model.policy.DefaultDestinationPolicy;
import model.policy.DefaultPathPolicy;

public class ChariotMoveRule extends PatternMoveRule {

    public ChariotMoveRule() {
        super(new DefaultPathPolicy(), new DefaultDestinationPolicy());
    }

    @Override
    protected List<MovePattern> patterns(Move move, Country country) {
        if(!isMovable(move)){
            return List.of();
        }

        return createPatterns(move);
    }

    private boolean isMovable(Move move){
        if(move.isStraight()){
            return true;
        }
        return isPalaceDiagonal(move);
    }

    private List<MovePattern> createPatterns(Move move) {
        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < move.distance(); i++) {
            steps.add(new Step(move.direction()));
        }

        return List.of(new MovePattern(steps, pathPolicy(), destinationPolicy()));
    }
}
