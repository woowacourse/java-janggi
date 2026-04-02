package domain.player;

import domain.place.piece.Side;

public class Player {
    private final Name name;
    private final Side side;

    public Player(String name, Side side) {
        this.name = new Name(name);
        this.side = side;
    }

    public String getName() {
        return name.name();
    }

    public Side getSide() {
        return side;
    }
}
