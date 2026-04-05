package model.move;

import java.util.ArrayList;
import java.util.List;

import model.board.Country;
import model.board.Palace;
import model.policy.DefaultDestinationPolicy;
import model.policy.DefaultPathPolicy;

public class ChariotMoveRule extends PatternMoveRule {

    public ChariotMoveRule() {
        super(new DefaultPathPolicy(), new DefaultDestinationPolicy());
    }

    @Override
    protected List<MovePattern> patterns(Move move, Country country) {
        if(!isMovable(move,country)){
            return List.of();
        }

        return createPatterns(move);
    }

    private boolean isMovable(Move move,Country country){
        if(move.isStraight()){
            return true;
        }
        Palace palace = Palace.from(country);
        return palace.isDiagonalMove(move);
    }

    private List<MovePattern> createPatterns(Move move) {
        List<Step> steps = new ArrayList<>();
        for (int i = 0; i < move.distance(); i++) {
            steps.add(new Step(move.direction()));
        }

        return List.of(new MovePattern(steps, pathPolicy(), destinationPolicy()));
    }
}
