package janggi.domain.game;

public class Player {

    private final String name;
    private final Side side;

    public Player(String name, Side side) {
        this.name = name;
        this.side = side;
    }

    public boolean isMyTurn(Turn turn) {
        return turn.isCurrent(this.side);
    }

    public PlayerDTO mapToVO() {
        return new PlayerDTO(name, side);
    }
}
