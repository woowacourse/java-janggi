package domain.position;

public record Position(int row, int col) {

    public Position {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("좌표는 음수일 수 없습니다.");
        }
    }
}
