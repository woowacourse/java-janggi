package janggi.dto;

import janggi.position.Position;
import java.util.Objects;

public class MoveDto {

    private final Position start;
    private final Position end;

    public MoveDto(final Position start, final Position end) {
        this.start = start;
        this.end = end;
    }

    public static MoveDto of(final String startRow, final String startColumn, final String endRow,
                             final String endColumn) {
        final Position start = Position.of(startRow, startColumn);
        final Position end = Position.of(endRow, endColumn);
        return new MoveDto(start, end);
    }

    public Position getStartPosition() {
        return start;
    }

    public Position getEndPosition() {
        return end;
    }

    public String getStartRow() {
        return String.valueOf(start.getRowValue());
    }

    public String getStartColumn() {
        return String.valueOf(start.getColumnValue());
    }

    public String getEndRow() {
        return String.valueOf(end.getRowValue());
    }

    public String getEndColumn() {
        return String.valueOf(end.getColumnValue());
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MoveDto moveDto = (MoveDto) o;
        return Objects.equals(start, moveDto.start) && Objects.equals(end, moveDto.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
