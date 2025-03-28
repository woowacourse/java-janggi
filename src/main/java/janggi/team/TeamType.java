package janggi.team;

public enum TeamType {

    HAN("한"),
    CHO("초");

    private final String title;

    TeamType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
