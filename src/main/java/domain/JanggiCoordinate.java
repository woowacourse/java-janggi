package domain;

public record JanggiCoordinate(int row, int col) {

    public JanggiCoordinate moveUp() {
        return new JanggiCoordinate(this.row - 1, this.col);
    }

    public JanggiCoordinate moveDown() {
        return new JanggiCoordinate(this.row + 1, this.col);
    }

    public JanggiCoordinate moveRight() {
        return new JanggiCoordinate(this.row, this.col + 1);
    }

    public JanggiCoordinate moveLeft() {
        return new JanggiCoordinate(this.row, this.col - 1);
    }

    public JanggiCoordinate moveRightUp() {
        return moveUp().moveRight();
    }

    public JanggiCoordinate moveLeftUp() {
        return moveUp().moveLeft();
    }

    public JanggiCoordinate moveRightDown() {
        return moveDown().moveRight();
    }

    public JanggiCoordinate moveLeftDown() {
        return moveDown().moveLeft();
    }

    public JanggiCoordinate move(Direction direction) {
        if (direction == Direction.UP) {
            return moveUp();
        }
        if (direction == Direction.RIGHT) {
            return moveRight();
        }
        if (direction == Direction.LEFT) {
            return moveLeft();
        }
        return moveDown();
    }

    @Override
    public String toString() {
        return "JanggiCoordinate{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
