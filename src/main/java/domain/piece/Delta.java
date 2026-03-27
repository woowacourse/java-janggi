package domain.piece;

public record Delta(int column, int row) {

    public static Delta up() {
        return new Delta(-1, 0);
    }

    public static Delta rightUp() {
        return new Delta(-1, 1);
    }

    public static Delta right() {
        return new Delta(0, 1);
    }

    public static Delta rightDown() {
        return new Delta(1, 1);
    }

    public static Delta down() {
        return new Delta(1, 0);
    }

    public static Delta leftDown() {
        return new Delta(1, -1);
    }

    public static Delta left() {
        return new Delta(0, -1);
    }

    public static Delta leftUp() {
        return new Delta(-1, -1);
    }
}
