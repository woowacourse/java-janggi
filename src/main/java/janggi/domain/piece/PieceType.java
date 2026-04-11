package janggi.domain.piece;

import janggi.domain.team.TeamType;
import java.util.function.Function;

public enum PieceType {

    GENERAL(General::new, 0),
    GUARD(Guard::new, 3),
    HORSE(Horse::new, 5),
    ELEPHANT(Elephant::new, 3),
    CHARIOT(Chariot::new, 13),
    CANNON(Cannon::new, 7),
    SOLDIER(Soldier::new, 2);

    private final Function<TeamType, Piece> function;
    private final int score;

    PieceType(Function<TeamType, Piece> function, int score) {
        this.function = function;
        this.score = score;
    }

    public Piece toPiece(final TeamType teamType) {
        return function.apply(teamType);
    }

    public int score() {
        return score;
    }
}
