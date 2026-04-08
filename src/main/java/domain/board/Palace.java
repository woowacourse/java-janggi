package domain.board;

import domain.vo.Position;

public class Palace {

    private final Position leftTop;
    private final Position rightTop;
    private final Position leftBottom;
    private final Position rightBottom;

    private Palace(final Team team) {
        if (team == Team.CHU) {
            this.leftTop = Position.of(2, 3);
            this.rightTop = Position.of(2, 5);
            this.leftBottom = Position.of(0, 3);
            this.rightBottom = Position.of(0, 5);
            return;
        }

        this.leftTop = Position.of(9, 3);
        this.rightTop = Position.of(9, 5);
        this.leftBottom = Position.of(7, 3);
        this.rightBottom = Position.of(7, 5);
    }

    public static Palace of(final Team team) {
        return new Palace(team);
    }

    public boolean isInPalace(final Position position) {
        return position.getCol() >= leftBottom.getCol() && position.getCol() <= rightTop.getCol()
                && position.getRow() <= rightTop.getRow() && position.getRow() >= leftBottom.getRow();
    }

    public boolean isDiagonalPoint(final Position position) {
        return leftBottom.equals(position) || leftTop.equals(position)
                || rightTop.equals(position) || rightBottom.equals(position)
                || isCenter(position);
    }

    private boolean isCenter(final Position position) {
        int centerRow = (leftTop.getRow() + leftBottom.getRow()) / 2;
        int centerCol = (leftTop.getCol() + rightTop.getCol()) / 2;
        return position.getRow() == centerRow && position.getCol() == centerCol;
    }
}
