package domain.board;

import java.util.Objects;

public class Intersection {

    private final Row row;
    private final File file;

    public Intersection(Row row, File file) {
        this.row = row;
        this.file = file;
    }

    public Intersection(int row, int file) {
        this(new Row(row), new File(file));
    }

    public boolean isOutOfBoard() {
        return row.isOutOfBoard() || file.isOutOfBoard();
    }

    public boolean isInBoard() {
        return !isOutOfBoard();
    }

    public int getRow() {
        return row.value();
    }

    public int getFile() {
        return file.value();
    }

    public boolean hasDifferentRow(int row) {
        return this.row.isDifferent(row);
    }

    public boolean hasDifferentFile(int file) {
        return this.file.isDifferent(file);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Intersection that = (Intersection) o;

        return Objects.equals(row, that.row) && Objects.equals(file, that.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, file);
    }

    @Override
    public String toString() {
        return "Intersection{" +
                "row=" + row +
                ", file=" + file +
                '}';
    }
}
