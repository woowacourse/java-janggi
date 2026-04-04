package janggi.domain.piece;

import janggi.domain.team.TeamType;
import java.util.function.Function;

public enum PieceType {

    GENERAL(General::new),
    GUARD(Guard::new),
    HORSE(Horse::new),
    ELEPHANT(Elephant::new),
    CHARIOT(Chariot::new),
    CANNON(Cannon::new),
    SOLDIER(Soldier::new);

    private final Function<TeamType, Piece> function;

    PieceType(Function<TeamType, Piece> function) {
        this.function = function;
    }

    public Piece toPiece(final TeamType teamType) {
        return function.apply(teamType);
    }

}
