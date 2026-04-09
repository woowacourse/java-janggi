package janggi.domain.side;

public enum TeamType {
    CHU("초나라"),
    HAN("한나라"),
    ;

    private final String name;

    TeamType(String name) {
        this.name = name;
    }

    public static TeamType from(String currentTurnTeam) {
        for (TeamType teamType : values()) {
            if (teamType.name.equals(currentTurnTeam)) {
                return teamType;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 팀 이름입니다.");
    }

    public String getName() {
        return name;
    }
}
