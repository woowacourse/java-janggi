package janggi.domain.turn;

public enum TurnStatus {

    CHU_WIN("CHU_WIN"),
    HAN_WIN("HAN_WIN"),
    DRAW("DRAW"),
    ;

    private final String format;

    TurnStatus(String format) {
        this.format = format;
    }

    public static TurnStatus from(String turnStatusFormat) {
        for (TurnStatus turnStatus : values()) {
            if (turnStatus.format.equals(turnStatusFormat)) {
                return turnStatus;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 턴 상태입니다.");
    }

    public String getFormat() {
        return format;
    }
}
