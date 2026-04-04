package domain;

import domain.vo.Position;

public class Palace {

    private final Position rightTop;
    private final Position leftBottom;

    private Palace(final Team team) {
        if (team == Team.CHU) {
            this.rightTop = Position.of(2, 5);
            this.leftBottom = Position.of(0, 3);
            return;
        }

        this.rightTop = Position.of(9, 5);
        this.leftBottom = Position.of(7, 3);
    }

    public static Palace of(final Team team) {
        return new Palace(team);
    }

    public boolean isInPalace(final Position position) {
        return position.getCol() >= leftBottom.getCol() && position.getCol() <= rightTop.getCol()
                && position.getRow() <= rightTop.getRow() && position.getRow() >= leftBottom.getRow();
    }
}
