package domain.coordination;

public record MoveDelta(int deltaColumn, int deltaRow) {

    public static MoveDelta between(Coordination from, Coordination to) {
        return new MoveDelta(from.differentColumn(to), from.differentRow(to));
    }

    public MoveDelta absolute() {
        return new MoveDelta(Math.abs(deltaColumn), Math.abs(deltaRow));
    }
}
