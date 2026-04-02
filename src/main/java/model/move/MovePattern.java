package model.move;

import java.util.ArrayList;
import java.util.List;
import model.board.Board;
import model.board.Country;
import model.board.Palace;
import model.policy.PathPolicy;
import model.position.Position;

public class MovePattern {
    private final List<Step> steps;
    private final PathPolicy pathPolicy;

    public MovePattern(List<Step> step, PathPolicy pathPolicy) {
        this.steps = step;
        this.pathPolicy = pathPolicy;
    }

    public boolean matches(Move move, Board board, Country country) {
        List<Position> path = positionsOnPath(move);
        if (!checkBoardRange(path, board) || !checkPathPolicy(path, board)) {
            return false;
        }

        Position destination = pickDestination(path, move);
        if (!destination.isSamePosition(move.to()) || !board.isInside(destination)) {
            return false;
        }

        return checkDestination(move, board, country);
    }

    private Position pickDestination(List<Position> path, Move move) {
        if (!path.isEmpty()) {
            return steps.getLast().move(path.getLast());
        }
        return steps.getLast().move(move.from());
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

    private boolean checkDestination(Move move, Board board, Country country) {
        return pathPolicy.validateDestination(move, board, country);
    }
}
