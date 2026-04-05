package domain;

public record Position(int row, int col) {

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

}
