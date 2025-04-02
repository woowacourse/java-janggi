package janggi.view;

import janggi.board.position.Position;

public class StartAndGoalPosition {
    private final Position start;
    private final Position goal;

    public StartAndGoalPosition(Position start, Position goal) {
        this.start = start;
        this.goal = goal;
    }

    public Position getStart() {
        return start;
    }

    public Position getGoal() {
        return goal;
    }
}
