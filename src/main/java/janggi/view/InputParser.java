package janggi.view;

import janggi.board.Position;

public class InputParser {
    private static final String START_GOAL_DELIMITER = " ";
    private static final String ROW_COLUMN_DELIMITER = ",";
    private static final int INDEX_AS_START_POSITION = 0;
    private static final int INDEX_AS_GOAL_POSITION = 1;
    private static final int INDEX_AS_COLUMN = 0;
    private static final int INDEX_AS_ROW = 1;

    public Position splitStartPosition(String input) {
        String[] positions = input.split(START_GOAL_DELIMITER);
        try {
            String[] startPosition = splitPositions(positions, INDEX_AS_START_POSITION);
            return getPosition(startPosition);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("[ERROR] 올바른 좌표 형식이 아닙니다.");
        }
    }

    public Position splitGoalPosition(String input) {
        String[] positions = input.split(START_GOAL_DELIMITER);
        try {
            String[] goalPosition = splitPositions(positions, INDEX_AS_GOAL_POSITION);
            return getPosition(goalPosition);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("[ERROR] 올바른 좌표 형식이 아닙니다.");
        }
    }

    private String[] splitPositions(String[] positions, int index) {
        return positions[index].split(ROW_COLUMN_DELIMITER);
    }

    private Position getPosition(String[] position) {
        try {
            int column = Integer.parseInt(position[INDEX_AS_COLUMN]);
            int row = Integer.parseInt(position[INDEX_AS_ROW]);
            return new Position(column, row);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자 좌표만 입력 가능합니다.");
        }
    }
}
