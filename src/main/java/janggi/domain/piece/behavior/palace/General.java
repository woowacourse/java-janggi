package janggi.domain.piece.behavior.palace;

public class General extends PalaceBehavior {

    @Override
    public String toName() {
        return "궁";
    }

    @Override
    public boolean isGeneral() {
        return true;
    }
}
