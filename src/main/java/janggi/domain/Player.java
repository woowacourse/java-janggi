package janggi.domain;

public class Player {

    private final String nickname;
    private final Side side;

    public Player(String name, Side side) {
        this.nickname = name;
        this.side = side;
    }
}
