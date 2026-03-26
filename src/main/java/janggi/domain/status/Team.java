package janggi.domain.status;

public enum Team {
    CHO("초"),
    HAN("한");

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
