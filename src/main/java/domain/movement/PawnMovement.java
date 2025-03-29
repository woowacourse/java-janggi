package domain.movement;

import domain.Move;
import domain.Moves;
import domain.Team;
import java.util.List;

public class PawnMovement {

    public List<Moves> findPossibleMoves(Team team) {
        return getMovesOptions(team);
    }

    private List<Moves> getMovesOptions(Team team) {
        if (team == Team.CHO) {
            return List.of(
                    Moves.create(Move.FRONT),
                    Moves.create(Move.RIGHT),
                    Moves.create(Move.LEFT),
                    Moves.create(Move.FRONT_LEFT),
                    Moves.create(Move.FRONT_RIGHT)
            );
        }
        return List.of(
                Moves.create(Move.BACK),
                Moves.create(Move.RIGHT),
                Moves.create(Move.LEFT),
                Moves.create(Move.BACK_LEFT),
                Moves.create(Move.BACK_RIGHT)
        );
    }
}
