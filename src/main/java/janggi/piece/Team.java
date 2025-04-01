package janggi.piece;

public enum Team {
    GREEN("초", 0),
    RED("한", 1.5);

    private final String displayName;
    private final double bonusPoint;

    Team(String displayName, double bonusPoint) {
        this.displayName = displayName;
        this.bonusPoint = bonusPoint;
    }

    public String getName() {
        return displayName;
    }

    public double getBonusPoint() {
        return bonusPoint;
    }

    public Team convertTeam() {
        if (this == GREEN) {
            return RED;
        }
        return GREEN;
    }

    public static Team convert(String teamName) {
        if (teamName.equals("GREEN")) {
            return Team.GREEN;
        }

        if (teamName.equals("RED")) {
            return Team.RED;
        }

        throw new IllegalArgumentException("[ERROR] 유효하지 않은 팀 이름입니다.: " + teamName);
    }
}
