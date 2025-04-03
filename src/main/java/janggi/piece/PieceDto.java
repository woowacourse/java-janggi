package janggi.piece;

public class PieceDto {
    private final int x;
    private final int y;
    private final String pieceType;
    private final String color;

    public PieceDto(final int x, final int y, final String pieceType, final String color) {
        this.x = x;
        this.y = y;
        this.pieceType = pieceType;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getPieceType() {
        return pieceType;
    }

    public String getColor() {
        return color;
    }
}
