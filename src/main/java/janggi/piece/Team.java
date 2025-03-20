package janggi.piece;

public enum Team {

    HAN("한"),
    CHO("초");

    private final String title;

    Team(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
