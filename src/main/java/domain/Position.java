package domain;

public record Position(int row, int col) {

    private static final Position palaceRedCenter = new Position(1, 4);
    private static final Position palaceGreenCenter = new Position(8, 4);

    public Position up() {
        return new Position(this.row - 1, this.col);
    }

    public Position down() {
        return new Position(this.row + 1, this.col);
    }

    public Position left() {
        return new Position(this.row, this.col - 1);
    }

    public Position right() {
        return new Position(this.row, this.col + 1);
    }

    public Position upCrossRight() {
        return new Position(this.row - 1, this.col + 1);
    }

    public Position upCrossLeft() {
        return new Position(this.row - 1, this.col - 1);
    }

    public Position downCrossRight() {
        return new Position(this.row + 1, this.col + 1);
    }

    public Position downCrossLeft() {
        return new Position(this.row + 1, this.col - 1);
    }

    public boolean isPalaceRedArea() {
        return isPalaceArea(palaceRedCenter);
    }

    public boolean isPalaceGreenArea() {
        return isPalaceArea(palaceGreenCenter);
    }

    private boolean isPalaceArea(Position palaceCenter) {
        if (this.equals(palaceCenter.upCrossLeft())) {
            return true;
        }
        if (this.equals(palaceCenter.up())) {
            return true;
        }
        if (this.equals(palaceCenter.upCrossRight())) {
            return true;
        }
        if (this.equals(palaceCenter.left())) {
            return true;
        }
        if (this.equals(palaceCenter)) {
            return true;
        }
        if (this.equals(palaceCenter.right())) {
            return true;
        }
        if (this.equals(palaceCenter.downCrossLeft())) {
            return true;
        }
        if (this.equals(palaceCenter.down())) {
            return true;
        }
        if (this.equals(palaceCenter.downCrossRight())) {
            return true;
        }

        return false;
    }
}
