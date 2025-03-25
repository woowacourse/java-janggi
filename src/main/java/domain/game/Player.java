package domain.game;

import domain.piece.Piece;
import domain.piece.Side;

public class Player {
    private Side side;

    public Player() {
        this.side = Side.CHO;
    }

    public Side getSide() {
        return side;
    }

    public void change() {
        if (this.side == Side.CHO) {
            this.side = Side.HAN;
            return;
        }
        this.side = Side.CHO;
    }

    public boolean isMyPiece(Piece piece) {
        return piece.isSameSide(this.side);
    }
}
