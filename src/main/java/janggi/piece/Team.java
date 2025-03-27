package janggi.piece;

public enum Team {

    HAN("한"),
    CHO("초");

    private final String title;

    Team(final String title) {
        this.title = title;
    }

    public Team getOppositeTeam() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }

    public String getTitle() {
        return title;
    }
}
