package model.move;

import java.util.List;
import model.board.Board;
import model.policy.DestinationPolicy;
import model.policy.PathPolicy;
import model.position.Position;

public class MovePattern {
    private final List<Step> steps;
    private final PathPolicy pathPolicy;
    private final DestinationPolicy destinationPolicy;

    public MovePattern(List<Step> step, PathPolicy pathPolicy, DestinationPolicy destinationPolicy) {
        this.steps = step;
        this.pathPolicy = pathPolicy;
        this.destinationPolicy = destinationPolicy;
    }

    public boolean matches(Move move, Board board) {
        return execute(move, board).isSamePosition(move.to())
                && destinationPolicy.validate(move, board);
    }

    private Position execute(Move move, Board board) {
        Position current = move.from();

        for (Step step : steps) {
            current = step.move(current);

//            if (!board.isInside(current)) {
//                return Position.invalid();
//            }

            if (!pathPolicy.check(current, board)) {
                return move.from();
            }
        }

        return current;
    }

    public List<Step> steps() {
        return steps;
    }
}
