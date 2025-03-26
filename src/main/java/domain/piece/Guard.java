package domain.piece;

import domain.Team;
import java.util.List;

public class Guard extends FixedMovePiece {

    private final List<Moves> moves = List.of(
            Moves.createMoves(Move.FRONT),
            Moves.createMoves(Move.BACK),
            Moves.createMoves(Move.RIGHT),
            Moves.createMoves(Move.LEFT)
    );

    public Guard(Team team) {
        super(team);
    }

    @Override
    public List<Moves> getMoveList() {
        return moves;
    }

    @Override
    public boolean isCanon() {
        return false;
    }

    @Override
    public boolean isKing() {
        return false;
    }
}
