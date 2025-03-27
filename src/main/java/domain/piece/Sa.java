package domain.piece;

import domain.Move;
import domain.Moves;
import domain.Position;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Sa extends FixedMovePiece {

    private static final List<Moves> movesOptions = List.of(
            Moves.create(Move.FRONT),
            Moves.create(Move.BACK),
            Moves.create(Move.RIGHT),
            Moves.create(Move.LEFT)
    );

    public Sa(Team team) {
        super(team);
    }

    @Override
    public List<Moves> getMovesOptions(Position startPosition) {
        List<Moves> moves = new ArrayList<>(movesOptions);
        if (startPosition.isPalaceTopLeft()) {
            moves.add(Moves.create(Move.BACK_RIGHT));
        }
        if (startPosition.isPalaceTopRight()) {
            moves.add(Moves.create(Move.BACK_LEFT));
        }
        if (startPosition.isPalaceBottomLeft()) {
            moves.add(Moves.create(Move.FRONT_RIGHT));
        }
        if (startPosition.isPalaceBottomRight()) {
            moves.add(Moves.create(Move.FRONT_LEFT));
        }
        if (startPosition.isPalaceCenter()) {
            moves.add(Moves.create(Move.BACK_RIGHT));
            moves.add(Moves.create(Move.BACK_LEFT));
            moves.add(Moves.create(Move.FRONT_RIGHT));
            moves.add(Moves.create(Move.FRONT_LEFT));
        }

        return moves.stream().filter(option -> option.isPossibleInPalace(startPosition)).toList();
    }
}
