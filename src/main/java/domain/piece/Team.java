package domain.piece;

public enum Team {

    HAN("한"),
    CHO("초"),
    ;

    private final String title;

    Team(final String title) {
        this.title = title;
    }

    public Team inverse() {
        if (this == CHO) {
            return HAN;
        }

        return CHO;
    }

    public String title() {
        return title;
    }
}
