package janggi.domain.side;

public enum TeamType {
    CHU("초나라"),
    HAN("한나라"),
    ;

    private final String name;

    TeamType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
