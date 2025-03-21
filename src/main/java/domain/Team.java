package domain;

public enum Team {
    GREEN("초나라"),
    RED("한나라");

    private final String title;

    Team(final String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
