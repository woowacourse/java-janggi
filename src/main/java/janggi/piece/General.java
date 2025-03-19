package janggi.piece;

import janggi.board.Position;

import java.util.Map;

public class General extends Piece {

    private static final String NAME = "궁";

    private static final int[][] dRows = {{1}, {0}, {0}, {-1}};
    private static final int[][] dColumns = {{0}, {-1}, {1}, {0}};

    public General(String team) {
        super(team);
    }

    @Override
    protected void validatePath(Map<Position, Piece> board, Position start, int pathIndex) {
        validateNonPieceOnPath(board, start, pathIndex);
    }

    @Override
    protected int[] getPathRows(int pathIndex) {
        return dRows[pathIndex];
    }

    @Override
    protected int[] getPathColumns(int pathIndex) {
        return dColumns[pathIndex];
    }

    @Override
    protected int[][] getAllPathRows() {
        return dRows;
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
        return other instanceof General;
    }
}
