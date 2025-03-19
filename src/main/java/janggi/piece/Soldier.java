package janggi.piece;

public class Soldier extends Piece {

    private static final String NAME = "졸/병";

    private static final int[][] dRows = {{1}, {0}, {0}};
    private static final int[][] dColumns = {{0}, {-1}, {1}};

    public Soldier(String team) {
        super(team);
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
}
