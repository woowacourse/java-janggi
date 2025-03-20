package janggi.view;

import janggi.board.Position;

public class InputParser {

    public Position splitStartPosition(String input) {
        String[] positions = input.split(" ");
        String[] startPosition = positions[0].split(",");
        return new Position(startPosition[0], startPosition[1]);
    }

    public Position splitGoalPosition(String input) {
        String[] positions = input.split(" ");
        String[] goalPosition = positions[1].split(",");
        return new Position(goalPosition[0], goalPosition[1]);
    }
}
