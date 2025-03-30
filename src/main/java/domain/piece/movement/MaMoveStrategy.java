package domain.piece.movement;

import domain.Move;
import domain.Moves;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;

public class MaMoveStrategy implements MoveStrategy {

    private static final List<Moves> movesOptions = List.of(
            Moves.create(Move.FRONT, Move.NO_LINE_FRONT_LEFT),
            Moves.create(Move.FRONT, Move.NO_LINE_FRONT_RIGHT),
            Moves.create(Move.BACK, Move.NO_LINE_BACK_LEFT),
            Moves.create(Move.BACK, Move.NO_LINE_BACK_RIGHT),
            Moves.create(Move.RIGHT, Move.NO_LINE_FRONT_RIGHT),
            Moves.create(Move.RIGHT, Move.NO_LINE_BACK_RIGHT),
            Moves.create(Move.LEFT, Move.NO_LINE_FRONT_LEFT),
            Moves.create(Move.LEFT, Move.NO_LINE_BACK_LEFT)
    );

    @Override
    public List<Moves> findPossibleMoves(Position src, Position dest, Team team) {
        return movesOptions;
    }
}
