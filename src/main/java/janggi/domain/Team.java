package janggi.domain;

public enum Team {
    CHO("초나라"),
    HAN("한나라");

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int calculateYPosition(Team team, int yPosition) {
        if(team == HAN) {
            return 11 - yPosition;
        }
        return yPosition;
    }
}
