package domain.piece.movement;

import domain.Move;
import domain.Moves;
import domain.piece.Position;
import domain.piece.Team;
import java.util.List;

public class PalaceMoveStrategy implements MoveStrategy {

    private static final List<Moves> movesOptions = List.of(
            Moves.create(Move.FRONT),
            Moves.create(Move.BACK),
            Moves.create(Move.RIGHT),
            Moves.create(Move.LEFT),
            Moves.create(Move.FRONT_LEFT),
            Moves.create(Move.FRONT_RIGHT),
            Moves.create(Move.BACK_LEFT),
            Moves.create(Move.BACK_RIGHT)
    );

    @Override
    public List<Moves> findPossibleMoves(Position src, Position dest, Team team) {
        return movesOptions.stream().filter(moves -> moves.isPossibleInPalace(src)).toList();
    }
}
