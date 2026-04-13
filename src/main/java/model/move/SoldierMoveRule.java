package model.move;

import java.util.ArrayList;
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
        List<MovePattern> patterns = new ArrayList<>();
        patterns.add(createPattern(Direction.LEFT));
        patterns.add(createPattern(Direction.RIGHT));
        patterns.add(createPattern(country.forward()));

        if(isPalaceForwardDiagonal(move,country)){
            patterns.add(createPattern(forwardLeft(country)));
            patterns.add(createPattern(forwardRight(country)));
        }

        return List.copyOf(patterns);
    }

    private boolean isPalaceForwardDiagonal(Move move, Country country){
        if(!isPalaceDiagonal(move)){
            return false;
        }
        return isForwardDiagonal(move, country);
    }

    private boolean isForwardDiagonal(Move move,Country country){
        Direction direction = move.direction();
        return direction == forwardLeft(country) || direction == forwardRight(country);
    }

    private Direction forwardLeft(Country country){
        if(country == Country.CHO){
            return Direction.UP_LEFT;
        }
        return Direction.DOWN_LEFT;
    }

    private Direction forwardRight(Country country){
        if(country==Country.CHO){
            return Direction.UP_RIGHT;
        }
        return Direction.DOWN_RIGHT;
    }

    private MovePattern createPattern(Direction direction){
        return new MovePattern(
                List.of(new Step(direction)),
                pathPolicy(),
                destinationPolicy()
        );
    }
}
