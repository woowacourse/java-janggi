package janggi.piece.players;

public enum Team {

    HAN("한"),
    CHO("초"),
    NONE("해당없음");

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
