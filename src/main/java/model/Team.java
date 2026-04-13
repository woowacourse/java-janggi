package model;

public enum Team {

    HAN("한나라"),
    CHO("초나라");

    private final String name;

    Team(String name) {
        this.name = name;
    }

    public static Team startTurn() {
        return CHO;
    }

    public static Team afterTurn() {
        return startTurn().opposite();
    }

    public boolean isHan() {
        return this == HAN;
    }

    public Team opposite() {
        if (isHan()) {
            return CHO;
        }
        return HAN;
    }

    public String getName() {
        return name;
    }
}
