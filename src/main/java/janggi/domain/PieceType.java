package janggi.domain;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Chariot;
import janggi.domain.piece.Elephant;
import janggi.domain.piece.General;
import janggi.domain.piece.Guard;
import janggi.domain.piece.Horse;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Soldier;
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
