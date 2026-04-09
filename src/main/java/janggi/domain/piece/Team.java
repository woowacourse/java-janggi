package janggi.domain.piece;

public enum Team {
    CHO("초", "C"),
    HAN("한", "H"),
    OTHER("빈", " ");

    private final String name;
    private final String prefix;

    Team(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public String findPrefix() {
        return prefix;
    }
}
 
