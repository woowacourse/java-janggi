package janggi.piece;

import janggi.Team;
import janggi.board.Position;
import java.util.Map;

public class Soldier extends Piece {

    private static final String NAME = "졸";
    private static final int[][] dRowsGreen = {{1}, {0}, {0}};
    private static final int[][] dRowsRed = {{-1}, {0}, {0}};
    private static final int[][] dColumns = {{0}, {-1}, {1}};

    public Soldier(Team team) {
        super(team);
    }

    @Override
    protected void validatePath(Map<Position, Piece> board, Position start, int pathIndex) {
        validateNonPieceOnPath(board, start, pathIndex);
    }

    @Override
    protected int[] getPathRows(int pathIndex) {
        if (team.equals("한나라")) {
            return dRowsRed[pathIndex];
        }
        return dRowsGreen[pathIndex];
    }

    @Override
    protected int[] getPathColumns(int pathIndex) {
        return dColumns[pathIndex];
    }

    @Override
    protected int[][] getAllPathRows() {
        if (team.equals("한나라")) {
            return dRowsRed;
        }
        return dRowsGreen;
    }

    @Override
    protected int[][] getAllPathColumns() {
        return dColumns;
    }

    @Override
    protected String getName() {
        return NAME;
    }

    @Override
    protected boolean isSameType(Piece other) {
        return other instanceof Soldier;
    }

    @Override
    protected void validatePieceOnGoal(Map<Position, Piece> board, Position goal) {
        validateSameTeamOnGoal(board, goal);
    }
}
