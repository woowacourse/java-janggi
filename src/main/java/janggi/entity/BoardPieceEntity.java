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

    public void updatePosition(int row, int col) {
        this.positionRow = row;
        this.positionCol = col;
    }

    public int getId() {
        return id;
    }

    public int getPositionRow() {
        return positionRow;
    }

    public int getPositionCol() {
        return positionCol;
    }

    public String getPieceType() {
        return pieceType;
    }

    public String getPieceColor() {
        return pieceColor;
    }

    public int getGameRoomId() {
        return gameRoomId;
    }
}
