package domain.place.piece;

public enum Side {
    CHO("C", 1, 1),
    HAN("H", 10, -1),
    EMPTY("", 1,1);

    private final String name;
    private final int startLine;
    private final int direction;

    Side(String name, int startLine, int direction) {
        this.name = name;
        this.startLine = startLine;
        this.direction = direction;

    }

    public String getName() {
        return name;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getDirection() {
        return direction;
    }
}