package domain;

public enum File {
    ZERO(0),
    NINE(9),
    EIGHT(8),
    SEVEN(7),
    SIX(6),
    FIVE(5),
    FOUR(4),
    THREE(3),
    TWO(2),
    ONE(1);

    private final int file;

    File(int file) {
        this.file = file;
    }

    public int getFile() {
        return file;
    }
}
