package domain.piece;

import domain.Move;
import domain.Moves;
import domain.Team;
import java.util.List;

public class Pawn extends FixedMovePiece {

    private final List<Moves> movesOptions;

    public Pawn(Team team) {
        super(team);
        if (team == Team.CHO) {
            this.movesOptions = List.of(
                    Moves.create(Move.FRONT),
                    Moves.create(Move.RIGHT),
                    Moves.create(Move.LEFT)
            );
            return;
        }
        this.movesOptions = List.of(
                Moves.create(Move.BACK),
                Moves.create(Move.RIGHT),
                Moves.create(Move.LEFT)
        );
    }

    @Override
    protected List<Moves> getMovesOptions() {
        return movesOptions;
    }
}
