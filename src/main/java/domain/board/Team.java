package domain.board;

public enum Team {
    CHU("초"),
    HAN("한");

    private final String name;

    Team(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
