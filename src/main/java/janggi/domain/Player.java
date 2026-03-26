package janggi.domain;

public class Player {

    private final String nickname;
    private final Side side;

    public Player(String name, Side side) {
        this.nickname = name;
        this.side = side;
    }

    public boolean isMyTurn(Turn turn) {
        return turn.isCurrent(this.side);
    }

    public String getNickname() {
        return nickname;
    }

    public Side getSide() {
        return side;
    }
}
