package domain.piece;

public enum Team {
    CHO("초"),
    HAN("한");

    private final String teamName;

    Team(String teamName) {
        this.teamName = teamName;
    }
}
