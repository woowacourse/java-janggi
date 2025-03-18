package janggi.piece;

import janggi.board.Position;

import java.util.Map;

public class Soldier extends Piece {

    private static final int[] dRow = {1, 0, 0};
    private static final int[] dColumn = {0, -1, 1};

    public Soldier(String team) {
        super(team);
    }

    @Override
    public void validateMovable(Map<Position, Piece> board, Position start, Position goal) {
        validateGoal(start, goal);
        validateSameTeamOnGoal(board, goal);
    }

    private void validateGoal(Position start, Position goal) {
        for (int i = 0; i < dRow.length; i++) {
            Position position = start;
            int column = dColumn[i];
            int row = dRow[i];
            position = position.plus(column, row);
            if (position == goal) {
                return;
            }
        }
        throw new IllegalArgumentException("[ERROR] 상은 해당 목적지로 이동할 수 없습니다.");
    }
}
