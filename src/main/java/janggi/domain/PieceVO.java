package janggi.domain;

public record PieceVO(Side side, PieceType type, String pieceNumber) {

    public boolean isSameSide(PieceVO other) {
        if (other == null) {
            return false;
        }
        return this.side == other.side();
    }

    public boolean isAlly(Side mySide) {
        return this.side == mySide;
    }

    public boolean isCannon() {
        return this.type == PieceType.CANNON;
    }
}
