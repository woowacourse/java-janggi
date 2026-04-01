package position;

public record Delta(int rowDelta, int columnDelta) {

    public Delta add(Delta delta) {
        return new Delta(rowDelta + delta.rowDelta, columnDelta + delta.columnDelta);
    }
}
