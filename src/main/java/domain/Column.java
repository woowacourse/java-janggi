package domain;

public record Column(int value) {

    public boolean isInsideBoard() {
        return value >= 0 && value <= 8;
    }
}
