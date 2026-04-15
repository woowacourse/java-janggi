package domain.coordination;

public record MoveDelta(int deltaColumn, int deltaRow) {

    private static final MoveDelta ONE_STEP_DIAGONAL = new MoveDelta(1, 1);

    public static MoveDelta between(Coordination from, Coordination to) {
        return new MoveDelta(from.differentColumn(to), from.differentRow(to));
    }

    public MoveDelta absolute() {
        return new MoveDelta(Math.abs(deltaColumn), Math.abs(deltaRow));
    }

    public boolean isOrthogonalOneStep() {
        MoveDelta absolute = absolute();
        return absolute.deltaColumn() + absolute.deltaRow() == 1;
    }

    public boolean isDiagonalOneStep() {
        return ONE_STEP_DIAGONAL.equals(absolute());
    }
}
