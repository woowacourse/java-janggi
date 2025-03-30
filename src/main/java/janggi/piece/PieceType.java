package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public enum PieceType {
    CHARIOT(Chariot::new,13),
    CANNON(Cannon::new,7),
    HORSE(Horse::new,5),
    ELEPHANT(Elephant::new,3),
    GUARD(Guard::new,3),
    SOLDIER(Soldier::new ,2),
    KING(King::new,0);

    private final BiFunction<Team, Position, Piece> instance;
    private final int score;

    PieceType(BiFunction<Team, Position, Piece> instance, int score) {
        this.instance = instance;
        this.score = score;
    }

    public Piece createInstance(Team team, Position position) {
        return instance.apply(team, position);
    }

    public int getScore() {
        return score;
    }
}
