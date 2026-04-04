package domain;

public enum Team {
    CHO("초"),
    HAN("한"),
    NONE("없음");

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
