package domain.position;

public record Position(int x, int y) {
    public Position {
        validate(x, y);
    }

    private void validate(int x, int y) {
        if (x < 0 || x > 8) {
            throw new IllegalArgumentException("x값은 0이상 8이하여야 합니다.");
        }
        if (y < 0 || y > 9) {
            throw new IllegalArgumentException("y값은 0이상 9이하여야 합니다.");
        }
    }

}
