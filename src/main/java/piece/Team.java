package piece;

public enum Team {

    RED("홍"),
    BLUE("청"),
    ;

    private static final String NOT_SUPPORTED_TEAM = "지원하지 않는 팀입니다.";

    Team(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return type;
    }

    public Team opposite() {
        if (this == RED) {
            return BLUE;
        }
        if (this == BLUE) {
            return RED;
        }
        throw new IllegalStateException(NOT_SUPPORTED_TEAM);
    }
}


