package janggiGame.piece.oneMovePiece;

import janggiGame.piece.Dynasty;
import janggiGame.piece.Type;

public class Pawn extends OneMovePiece {

    public Pawn(Dynasty dynasty) {
        super(dynasty, Type.PAWN);
    }

    @Override
    public void validateRoute(int dx, int dy) {
        if (Math.abs(dx) + Math.abs(dy) != 1) {
            throw new UnsupportedOperationException("[ERROR] 병이 이동할 수 있는 목적지가 아닙니다.");
        }

        if (dynasty == Dynasty.HAN && dy > 0) {
            throw new UnsupportedOperationException("[ERROR] 병은 뒤로 이동할 수 없습니다.");
        }

        if (dynasty == Dynasty.CHO && dy < 0) {
            throw new UnsupportedOperationException("[ERROR] 병은 뒤로 이동할 수 없습니다.");
        }
    }
}
