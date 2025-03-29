package domain.piece;

import domain.Team;
import java.util.function.Function;

public enum PieceType {
    GENERAL(General::new),
    SOLDIER(Soldier::new),
    GUARD(Guard::new),
    ELEPHANT(Elephant::new),
    HORSE(Horse::new),
    CANNON(Cannon::new),
    CHARIOT(Chariot::new);

    private final Function<Team, Piece> function;

    PieceType(final Function<Team, Piece> function) {
        this.function = function;
    }

    public static Piece find(final PieceType pieceType, final Team team) {
        return pieceType.function.apply(team);
    }
}

