package domain.piece;

import domain.Move;
import domain.Moves;
import domain.Position;
import domain.Team;
import java.util.ArrayList;
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
    protected List<Moves> getMovesOptions(Position startPosition) {
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
        if (startPosition.isPalaceCenter() && team == Team.CHO) {
            moves.add(Moves.create(Move.FRONT_RIGHT));
            moves.add(Moves.create(Move.FRONT_LEFT));
        }
        if (startPosition.isPalaceCenter() && team == Team.HAN) {
            moves.add(Moves.create(Move.BACK_RIGHT));
            moves.add(Moves.create(Move.BACK_LEFT));
        }

        return moves;
    }
}
