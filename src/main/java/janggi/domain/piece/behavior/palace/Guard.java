package janggi.domain.piece.behavior.palace;

public final class Guard extends PalaceBehavior {

    @Override
    public String toName() {
        return "사";
    }

    @Override
    public int toScore() {
        return 3;
    }
}
