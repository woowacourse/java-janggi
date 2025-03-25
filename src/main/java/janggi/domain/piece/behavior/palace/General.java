package janggi.domain.piece.behavior.palace;

public class General extends PalaceBehavior {

    @Override
    public String toName() {
        return "궁";
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
