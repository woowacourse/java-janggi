package janggi.domain.position;

public class Movement {

    private final Position from;
    private final Position to;

    public Movement(Position from, Position to) {
        validateSame(from, to);
        this.from = from;
        this.to = to;
    }

    public int calculateRowDiff() {
        return to.getRowValue() - from.getRowValue();
    }

    public int calculateColumnDiff() {
        return to.getColumnValue() - from.getColumnValue();
    }

    public Position getFrom() {
        return from;
    }

    public Position getTo() {
        return to;
    }

    private void validateSame(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 출발 좌표와 도착 좌표는 같을 수 없습니다.");
        }
    }
}
