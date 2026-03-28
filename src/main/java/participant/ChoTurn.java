package participant;

import pieces.FullPiece;
import pieces.Piece;
import pieces.Side;

public class ChoTurn implements Turn {

    @Override
    public Turn move() {
        return new HanTurn();
    }

    @Override
    public boolean isMatchSide(Side side) {
        return side == Side.CHO;
    }

    @Override
    public void validateSide(Piece piece) {
        if (piece.isEmpty()) {
            throw new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다.");
        }

        FullPiece fullPiece = (FullPiece) piece;
        if (!fullPiece.isSameSide(Side.CHO)) {
            throw new IllegalArgumentException("초 진영의 기물만 이동시킬 수 있습니다.");
        }
    }
}
