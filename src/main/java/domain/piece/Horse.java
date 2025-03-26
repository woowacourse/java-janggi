package domain.piece;

import domain.Team;
import java.util.List;

public class Horse extends FixedMovePiece {

    private final List<Moves> moves =
            List.of(
                    Moves.createMoves(Move.FRONT, Move.FRONT_LEFT),
                    Moves.createMoves(Move.FRONT, Move.FRONT_RIGHT),
                    Moves.createMoves(Move.BACK, Move.BACK_LEFT),
                    Moves.createMoves(Move.BACK, Move.BACK_RIGHT),
                    Moves.createMoves(Move.RIGHT, Move.FRONT_RIGHT),
                    Moves.createMoves(Move.RIGHT, Move.BACK_RIGHT),
                    Moves.createMoves(Move.LEFT, Move.FRONT_LEFT),
                    Moves.createMoves(Move.LEFT, Move.BACK_LEFT));

    public Horse(Team team) {
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
