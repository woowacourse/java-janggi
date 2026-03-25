package domain;

public abstract class PieceImpl implements Piece {
    private final Side side;

    public PieceImpl(Side side) {
        this.side = side;
    }

    @Override
    public boolean isHan() {
        return side.isHan();
    }

    @Override
    public boolean isCho() {
        return side.isCho();
    }
}
