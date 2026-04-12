package janggi.persistence.entity;

public class PieceEntity {
    private Long id;
    private String gameId;
    private String pieceName;
    private String pieceCamp;
    private Integer rowIndex;
    private Integer columnIndex;

    public PieceEntity(Long id, String gameId, String pieceName, String pieceCamp, Integer rowIndex, Integer columnIndex) {
        this.id = id;
        this.gameId = gameId;
        this.pieceName = pieceName;
        this.pieceCamp = pieceCamp;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    public Long id() {
        return id;
    }

    public String gameId() {
        return gameId;
    }

    public String pieceName() {
        return pieceName;
    }

    public String pieceCamp() {
        return pieceCamp;
    }

    public Integer row() {
        return rowIndex;
    }

    public Integer column() {
        return columnIndex;
    }
}
