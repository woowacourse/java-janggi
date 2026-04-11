package domain.movement.vo;

public record Delta(int columnDelta, int rowDelta) {
    public static final Delta ZERO = new Delta(0, 0);

    public Delta add(Delta other) {
        return new Delta(columnDelta + other.columnDelta(), rowDelta + other.rowDelta());
    }
}
