package piece;

public enum Team {

    RED("홍"),
    BLUE("청"),
    ;

    Team(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return type;
    }
}


