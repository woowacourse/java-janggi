package infra.entity;

import java.util.Objects;

public class PieceEntity {

    private final Long id;
    private final String dtype;
    private final String team;
    private final int columnIndex;
    private final int rowIndex;

    public PieceEntity(
        final Long id,
        final String dtype,
        final String team,
        final int columnIndex,
        final int rowIndex
    ) {
        this.id = id;
        this.dtype = dtype;
        this.team = team;
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }

    public PieceEntity(
        final String dtype,
        final String team,
        final int columnIndex,
        final int rowIndex
    ) {
        this(null, dtype, team, columnIndex, rowIndex);
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
