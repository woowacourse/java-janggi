package model;

public enum Team {
    HAN("한나라"), CHO("초나라");

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Team next() {
        if (this == HAN) {
            return CHO;
        }
        return HAN;
    }
}
