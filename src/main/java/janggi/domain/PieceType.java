package janggi.domain;

public enum PieceType {
    ZOL("졸");
    private final String name;

    PieceType(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
