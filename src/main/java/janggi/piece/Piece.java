package janggi.piece;

import janggi.Team;
import janggi.board.Position;

import java.util.Map;

public abstract class Piece {

    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public boolean isSameTeam(Team team) {
        return this.team == team;
    }

    public void validateMovable(Map<Position, Piece> board, Position start, Position goal) {
        int pathIndex = calculatePathIndex(start, goal);
        validatePath(board, start, pathIndex);
        validatePieceOnGoal(board, goal);

    }

    private int calculatePathIndex(Position start, Position goal) {
        int[][] dRows = getAllPathRows();
        int[][] dColumns = getAllPathColumns();
        for (int i = 0; i < dRows.length; i++) {
            int[] dRow = dRows[i];
            int[] dColumn = dColumns[i];
            Position position = start;
            for (int j = 0; j < dRow.length; j++) {
                position = position.plus(dColumn[j], dRow[j]);
            }
            if (position.equals(goal)) {
                return i;
            }
        }
        throw new IllegalArgumentException("[ERROR] %s은/는 해당 목적지로 이동할 수 없습니다.".formatted(getName()));
    }

    protected void validateNonPieceOnPath(Map<Position, Piece> board, Position start, int pathIndex) {
        int[] rows = getPathRows(pathIndex);
        int[] columns = getPathColumns(pathIndex);
        Position position = start;
        for (int j = 0; j < rows.length - 1; j++) {
            position = position.plus(columns[j], rows[j]); // 길목의 좌표
            boolean existsPiece = board.containsKey(position);
            if (existsPiece) {
                throw new IllegalArgumentException("[ERROR] 해당 경로에 다른 기물이 있어 이동할 수 없습니다.");
            }
        }
    }

    protected void validateSameTeamOnGoal(Map<Position, Piece> board, Position goal) {
        Piece other = board.get(goal);
        if (other == null) {
            return;
        }

        if (other.isSameTeam(team)) {
            throw new IllegalArgumentException("[ERROR] 목적지에 같은 진영의 기물이 있어 이동할 수 없습니다.");
        }
    }

    @Override
    public String toString() {
        return getName();
    }

    protected abstract void validatePath(Map<Position, Piece> board, Position start, int pathIndex);

    protected abstract int[] getPathRows(int pathIndex);

    protected abstract int[] getPathColumns(int pathIndex);

    protected abstract int[][] getAllPathRows();

    protected abstract int[][] getAllPathColumns();

    protected abstract String getName();

    protected abstract boolean isSameType(Piece other);

    protected abstract void validatePieceOnGoal(Map<Position, Piece> board, Position goal);

    public abstract boolean isGeneral();
}
