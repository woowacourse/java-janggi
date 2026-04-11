package model.board;

public enum Status {
    FINISHED, DRAW, PLAYING;

    public static Status fromStatus(String name) {
        for (Status status : Status.values()) {
            if (status.name().equals(name)) {
                return status;
            }
        }
        throw new IllegalArgumentException("[ERROR] 없는 상태입니다.");
    }
}
