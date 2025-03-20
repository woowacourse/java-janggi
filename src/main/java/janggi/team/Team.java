package janggi.team;

public enum Team {
    CHO("초"),
    HAN("한");

    private final String value;

    Team(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
