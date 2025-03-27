package janggi.domain.piece.behavior.palace;

import janggi.domain.piece.PieceType;

public class General extends PalaceBehavior {

    @Override
    public String toName() {
        return PieceType.GENERAL.getName();
    }

    @Override
    public int toScore() {
        throw new UnsupportedOperationException("궁은 점수를 지원하지 않습니다!");
    }

    @Override
    public boolean isGeneral() {
        return true;
    }
}
