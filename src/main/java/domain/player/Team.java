package domain.player;

public enum Team {
    HAN("한", TeamColor.RED),
    CHO("초", TeamColor.BLUE),
    ;

    private final String name;
    private final TeamColor color;

    Team(String name, TeamColor color) {
        this.name = name;
        this.color = color;
    }

    public TeamColor getColor() {
        return color;
    }

    public String getName() {
        return name;
    }
}
