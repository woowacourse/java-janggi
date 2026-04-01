package domain;

public record Row(int value) {

    public boolean isInsideBoard() {
        return value >= 0 && value <= 9;
    }
}
