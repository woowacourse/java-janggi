package domain.vo;

public enum Team {
    CHO("초(CHO)"),
    HAN("한(HAN)");

    private String teamName;

    Team(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }
}
