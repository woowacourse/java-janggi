package janggi.model.piece;

public record Score(
        int value
) {
    public Score(int value) {
        if (value < 2 || value > 13) {
            throw new IllegalArgumentException("유효하지 않은 값입니다.");
        }

        this.value = value;
    }
}
