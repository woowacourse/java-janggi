package janggi.domain.piece;

import janggi.domain.game.Team;
import java.util.function.Function;

public enum Type {

    CANNON(Cannon::new),
    CHARIOT(Chariot::new),
    ELEPHANT(Elephant::new),
    GENERAL(General::new),
    GUARD(Guard::new),
    HORSE(Horse::new),
    SOLDIER(Soldier::new);

    private final Function<Team, Piece> constructor;

    Type(final Function<Team, Piece> constructor) {
        this.constructor = constructor;
    }

    public Function<Team, Piece> getConstructor() {
        return constructor;
    }
}
