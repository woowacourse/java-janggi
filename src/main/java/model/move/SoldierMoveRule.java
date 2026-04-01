package model.move;

import java.util.List;

import model.board.Country;
import model.policy.DefaultDestinationPolicy;
import model.policy.DefaultPathPolicy;

public class SoldierMoveRule extends PatternMoveRule {

    public SoldierMoveRule() {
        super(new DefaultPathPolicy(), new DefaultDestinationPolicy());
    }

    @Override
    protected List<MovePattern> patterns(Move move, Country country) {
        return List.of(
                new MovePattern(List.of(new Step(Direction.LEFT)), pathPolicy(), destinationPolicy()),
                new MovePattern(List.of(new Step(Direction.RIGHT)), pathPolicy(), destinationPolicy()),
                new MovePattern(List.of(new Step(country.forward())), pathPolicy(), destinationPolicy())
        );
    }

}
