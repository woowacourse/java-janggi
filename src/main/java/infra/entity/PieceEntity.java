package infra.entity;

import java.util.Objects;

public class PieceEntity {

    private final Long id;
    private final String dtype;
    private final String team;
    private final int columnIndex;
    private final int rowIndex;

    public PieceEntity(
        final String dtype,
        final String team,
        final int columnIndex,
        final int rowIndex
    ) {
        this(null, dtype, team, columnIndex, rowIndex);
    }

    public PieceEntity(
        final Long id,
        final String dtype,
        final String team,
        final int columnIndex,
        final int rowIndex
    ) {
        validateNotBlank(dtype, team);
        validateNonNegative(columnIndex, rowIndex);
        this.id = id;
        this.dtype = dtype;
        this.team = team;
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }

    private void validateNotBlank(
        final String dtype,
        final String team
    ) {
        if (dtype == null || dtype.isBlank() || team == null || team.isBlank()) {
            throw new IllegalArgumentException("PieceEntity의 dtype과 team은 null이거나 공백일 수 없습니다.");
        }
    }

    private void validateNonNegative(
        final int columnIndex,
        final int rowIndex
    ) {
        if (columnIndex < 0 || rowIndex < 0) {
            throw new IllegalArgumentException("columnIndex와 rowIndex는 0 이상이어야 합니다.");
        }
    }

    public Long getId() {
        return id;
    }

    public String getDtype() {
        return dtype;
    }

    public String getTeam() {
        return team;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final PieceEntity that = (PieceEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
