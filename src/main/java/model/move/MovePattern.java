package model.move;

import java.util.ArrayList;
import java.util.List;
import model.board.Board;
import model.policy.PathPolicy;
import model.position.Position;

public class MovePattern {
    private final List<Step> steps;
    private final PathPolicy pathPolicy;

    public MovePattern(List<Step> step, PathPolicy pathPolicy) {
        this.steps = step;
        this.pathPolicy = pathPolicy;
    }

    public boolean matches(Move move, Board board) {
        List<Position> path = positionsOnPath(move);
        if (!checkBoardRange(path, board) || !checkPathPolicy(path, board)) {
            return false;
        }
        Position destination = steps.getLast().move(move.from());
        if (!path.isEmpty()) {
            destination = steps.getLast().move(path.getLast());
        }
        if (!destination.isSamePosition(move.to()) || !board.isInside(destination)) {
            return false;
        }

        return checkDestination(move, board);
    }

    private List<Position> positionsOnPath(Move move) {
        List<Position> path = new ArrayList<>();
        Position current = move.from();
        for (int i = 0; i < steps.size() - 1; i++) {
            Step step = steps.get(i);
            current = step.move(current);
            path.add(current);
        }
        return path;
    }

    private boolean checkBoardRange(List<Position> path, Board board) {
        return path.stream().allMatch(board::isInside);
    }

    private boolean checkPathPolicy(List<Position> path, Board board) {
        return pathPolicy.validatePath(path, board);
    }

    private boolean checkDestination(Move move, Board board) {
        return pathPolicy.validateDestination(move, board);
    }
}
