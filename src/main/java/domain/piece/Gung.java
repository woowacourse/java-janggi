package domain.piece;

import domain.Move;
import domain.Moves;
import domain.Team;
import java.util.List;

public class Gung extends FixedMovePiece {

    private static final List<Moves> movesOptions = List.of(
            Moves.create(Move.FRONT),
            Moves.create(Move.BACK),
            Moves.create(Move.RIGHT),
            Moves.create(Move.LEFT)
    );

    public Gung(Team team) {
        super(team);
    }

    @Override
    protected List<Moves> getMovesOptions() {
        return movesOptions;
    }
}
