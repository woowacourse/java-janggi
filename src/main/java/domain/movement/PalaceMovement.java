package domain.movement;

import domain.Move;
import domain.Moves;
import domain.Position;
import java.util.List;

public class PalaceMovement {

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

    public List<Moves> calculatePath(Position src) {
        return movesOptions.stream()
                .filter(moves -> moves.isPossibleInPalace(src)).toList();
    }
}
