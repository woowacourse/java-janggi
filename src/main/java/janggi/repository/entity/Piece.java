package janggi.repository.entity;

public class Piece {
    private Long id;
    private final Long gameId;
    private final String type;
    private final String side;
    private final int rowIdx;
    private final int colIdx;

    public Piece(Long gameId, String type, String side, int rowIdx, int colIdx) {
        this.gameId = gameId;
        this.type = type;
        this.side = side;
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
    }

    public Piece(Long id, Long gameId, String type, String side, int rowIdx, int colIdx) {
        this.id = id;
        this.gameId = gameId;
        this.type = type;
        this.side = side;
        this.rowIdx = rowIdx;
        this.colIdx = colIdx;
    }

    public Long getId() {
        return id;
    }

    public Long getGameId() {
        return gameId;
    }

    public String getType() {
        return type;
    }

    public String getSide() {
        return side;
    }

    public int getRowIdx() {
        return rowIdx;
    }

    public int getColIdx() {
        return colIdx;
    }
}
