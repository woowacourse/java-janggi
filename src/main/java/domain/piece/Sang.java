package domain.piece;

import domain.Move;
import domain.Moves;
import domain.Team;
import java.util.List;

public class Sang extends FixedMovePiece {

    private static final List<Moves> movesOptions = List.of(
            Moves.create(Move.FRONT, Move.FRONT_LEFT, Move.FRONT_LEFT),
            Moves.create(Move.FRONT, Move.FRONT_RIGHT, Move.FRONT_RIGHT),
            Moves.create(Move.BACK, Move.BACK_LEFT, Move.BACK_LEFT),
            Moves.create(Move.BACK, Move.BACK_RIGHT, Move.BACK_RIGHT),
            Moves.create(Move.RIGHT, Move.FRONT_RIGHT, Move.FRONT_RIGHT),
            Moves.create(Move.RIGHT, Move.BACK_RIGHT, Move.BACK_RIGHT),
            Moves.create(Move.LEFT, Move.FRONT_LEFT, Move.FRONT_LEFT),
            Moves.create(Move.LEFT, Move.BACK_LEFT, Move.BACK_LEFT)
    );

    public Sang(Team team) {
        super(team);
    }

    @Override
    protected List<Moves> getMovesOptions() {
        return movesOptions;
    }
}
