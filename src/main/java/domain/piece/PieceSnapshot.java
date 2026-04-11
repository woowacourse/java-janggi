package domain.piece;

public class PieceSnapshot {
    private PieceType pieceType;
    private Side side;
    private int positionX;
    private int positionY;

    private PieceSnapshot(PieceType pieceType, Side side, int positionX, int positionY) {
        this.pieceType = pieceType;
        this.side = side;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public static PieceSnapshot of(PieceType pieceType, Side side, int positionX, int positionY) {
        return new PieceSnapshot(pieceType, side, positionX, positionY);
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public Side getSide() {
        return side;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
}
