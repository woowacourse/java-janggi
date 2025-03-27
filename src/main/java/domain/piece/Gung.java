package domain.piece;

import domain.Move;
import domain.Moves;
import domain.Position;
import domain.Team;
import java.util.ArrayList;
import java.util.List;

public class Gung extends FixedMovePiece {

    private static final List<Moves> movesOptions = new ArrayList<>(List.of(
            Moves.create(Move.FRONT),
            Moves.create(Move.BACK),
            Moves.create(Move.RIGHT),
            Moves.create(Move.LEFT)
    ));

    public Gung(Team team) {
        super(team);
    }

    @Override
    protected List<Moves> getMovesOptions(Position startPosition) {
        List<Moves> moves = new ArrayList<>(movesOptions);
        addMove(startPosition, moves);

        return moves.stream().filter(option -> option.isPossibleInPalace(startPosition)).toList();
    }

    private static void addMove(Position startPosition, List<Moves> moves) {
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
            moves.addAll(List.of(
                    Moves.create(Move.BACK_RIGHT),
                    Moves.create(Move.BACK_LEFT),
                    Moves.create(Move.FRONT_RIGHT),
                    Moves.create(Move.FRONT_LEFT)
            ));
        }
    }
}
