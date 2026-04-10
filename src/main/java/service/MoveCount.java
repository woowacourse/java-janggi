package service;

public record MoveCount(int count) {

    public MoveCount {
        validateRange(count);
    }

    private void validateRange(int count) {
        if (count < 0) {
            throw new IllegalStateException("기물 움직임 횟수는 양수이어야 합니다.");
        }
    }

    public static MoveCount init() {
        return new MoveCount(0);
    }

    public boolean canUndo() {
        return count > 0;
    }

    public MoveCount unDo() {
        return new MoveCount(count - 1);
    }

    public MoveCount move() {
        return new MoveCount(count + 1);
    }
}
