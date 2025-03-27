package janggi.domain.position;

public record RawPosition(int x, int y) {

    public RawPosition up() {
        return new RawPosition(x, y + 1);
    }

    public RawPosition down() {
        return new RawPosition(x, y - 1);
    }

    public RawPosition left() {
        return new RawPosition(x - 1, y);
    }

    public RawPosition right() {
        return new RawPosition(x + 1, y);
    }

    public RawPosition upRightDiagonal() {
        return new RawPosition(x + 1, y + 1);
    }

    public RawPosition downRightDiagonal() {
        return new RawPosition(x + 1, y - 1);
    }

    public RawPosition upLeftDiagonal() {
        return new RawPosition(x - 1, y + 1);
    }

    public RawPosition downLeftDiagonal() {
        return new RawPosition(x - 1, y - 1);
    }
}
