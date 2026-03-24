package janggi.domain.piece;

public enum Team {
    CHO("초"),
    HAN("한"),
    OTHER("빈");

    private final String name;

    Team(String name) {
        this.name = name;
    }
}
