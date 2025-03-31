package entity;

public class BoardEntity {
    private long id;
    private int rowIndex;
    private int columnIndex;
    private long pieceId;

    public BoardEntity(long id, int rowIndex, int columnIndex, long pieceId) {
        this.id = id;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.pieceId = pieceId;
    }

    public long getId() {
        return id;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public long getPieceId() {
        return pieceId;
    }
}
