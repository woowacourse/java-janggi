package janggi;

public record CurvedMovement(
        int straightMovement,
        int diagonalMovement
) {
    public CurvedMovement {
        if (straightMovement <= 0) {
            throw new IllegalArgumentException("직선으로 1칸 이상 움직여야 합니다");
        }

        if (straightMovement >= diagonalMovement) {
            throw new IllegalArgumentException("대각선 움직임이 직선 움직임보다 커야 합니다");
        }
    }

    public boolean matches(int straightMovement, int diagonalMovement) {
        return this.straightMovement == straightMovement && this.diagonalMovement == diagonalMovement;
    }
}
