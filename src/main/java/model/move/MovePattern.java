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
        Position current = checkPath(move, board);
        if (current.isInValid()) {
            return false;
        }
        current = steps.getLast().move(current);
        if (!current.isSamePosition(move.to())) {
            return false;
        }

        return checkDestination(move, board, current);
    }

    private boolean checkDestination(Move move, Board board, Position current) {
        if (!checkBoardRange(board, current)) {
            return false;
        }

        return destinationPolicy.validate(move, board, pathPolicy);
    }

    private Position checkPath(Move move, Board board) {
        Position current = move.from();
        for (int i = 0; i < steps.size() - 1; i++) {
            Step step = steps.get(i);
            current = checkStep(step, board, current);
        }
        return current;
    }

    private Position checkStep(Step step, Board board, Position current) {
        current = step.move(current);
        if (!checkBoardRange(board, current) || !checkPathPolicy(board, current)) {
            return Position.inValid();
        }
        return current;
    }

    private boolean checkBoardRange(Board board, Position current) {
        return board.isInside(current);
    }

    private boolean checkPathPolicy(Board board, Position current) {
        return pathPolicy.check(current, board);
    }
}
