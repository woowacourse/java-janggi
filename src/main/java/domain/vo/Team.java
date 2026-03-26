package domain.vo;

public enum Team {
    CHO("초(CHO)", "초"),
    HAN("한(HAN)", "한");

    private final String teamName;
    private final String prefix;

    Team(String teamName, String prefix) {
        this.teamName = teamName;
        this.prefix = prefix;
    }

    public String getTeamName() {
        return teamName;
    }

    public String getPrefix() {
        return prefix;
    }

    public Team getEnemy() {
        if (this == CHO) {
            return HAN;
        }
        return CHO;
    }
}
