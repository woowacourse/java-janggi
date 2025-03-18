package janggi.piece;

import janggi.board.Position;
import java.util.Map;

public class Elephant extends Piece {

    private static final int[][] dRows = {{1, 1, 1}, {1, 1, 1}, {0, 1, 1}, {0, -1, -1}, {-1, -1, -1}, {-1, -1, -1}, {0, -1, -1}, {0, 1, 1}};
    private static final int[][] dColumns = {{0, -1, -1}, {0, 1, 1}, {1, 1, 1}, {1, 1, 1}, {0, 1, 1}, {0, 1, 1}, {-1, -1, -1}, {-1, -1, -1}};

    public Elephant(String team) {
        super(team);
    }

    @Override
    public void validateMovable(Map<Position, Piece> board, Position start, Position goal) {
        int pathIndex = calculatePathIndex(start, goal);
        validatePath(board, start, pathIndex);
        validateSameTeamOnGoal(board, goal);
    }

    private int calculatePathIndex(Position start, Position goal) {
        for (int i = 0; i < dRows.length; i++) {
            int[] columns = dColumns[i];
            int[] rows = dRows[i];
            Position position = start;
            for (int j = 0; j < rows.length; j++) {
                position = position.plus(columns[j], rows[j]);
            }
            if (position == goal) {
                return i;
            }
        }
        throw new IllegalArgumentException("[ERROR] 상은 해당 목적지로 이동할 수 없습니다.");
    }

    private void validatePath(Map<Position, Piece> board, Position start, int pathIndex) {
        int[] rows = dRows[pathIndex];
        int[] columns = dColumns[pathIndex];
        Position position = start;
        for (int j = 0; j < rows.length - 1; j++) {
            position = position.plus(columns[j], rows[j]); // 길목의 좌표
            boolean existsPiece = board.containsKey(position);
            if (existsPiece) {
                throw new IllegalArgumentException("[ERROR] 해당 경로에 다른 기물이 있어 이동할 수 없습니다.");
            }
        }
    }
}
