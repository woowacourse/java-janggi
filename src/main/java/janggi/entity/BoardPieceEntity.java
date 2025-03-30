package janggi.entity;

public class BoardPieceEntity {
    private int id;
    private int gameRoomId;
    private int positionRow;
    private int positionCol;
    private String pieceType;
    private String pieceColor;

    public BoardPieceEntity(int id, int gameRoomId, int positionRow, int positionCol, String pieceType,
                            String pieceColor) {
        this.id = id;
        this.gameRoomId = gameRoomId;
        this.positionRow = positionRow;
        this.positionCol = positionCol;
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public void setPositionRow(int positionRow) {
        this.positionRow = positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    public void setPositionCol(int positionCol) {
        this.positionCol = positionCol;
    }

    public String getPieceType() {
        return pieceType;
    }

    public void setPieceType(String pieceType) {
        this.pieceType = pieceType;
    }

    public String getPieceColor() {
        return pieceColor;
    }

    public void setPieceColor(String pieceColor) {
        this.pieceColor = pieceColor;
    }

    public int getGameRoomId() {
        return gameRoomId;
    }

    public void setGameRoomId(int gameRoomId) {
        this.gameRoomId = gameRoomId;
    }
}
