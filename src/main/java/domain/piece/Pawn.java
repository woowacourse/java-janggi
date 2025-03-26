package domain.piece;

import domain.Team;
import java.util.List;

public class Pawn extends FixedMovePiece {

    private final List<Moves> blueTeamMoves = List.of(
            Moves.createMoves(Move.FRONT),
            Moves.createMoves(Move.BACK),
            Moves.createMoves(Move.RIGHT),
            Moves.createMoves(Move.LEFT)
    );
    private final List<Moves> redTeamMoves = List.of(
            Moves.createMoves(Move.FRONT),
            Moves.createMoves(Move.BACK),
            Moves.createMoves(Move.RIGHT),
            Moves.createMoves(Move.LEFT)
    );

    public Pawn(Team team) {
        super(team);
    }

    @Override
    public List<Moves> getMoveList() {
        if (this.team == Team.BLUE) {
            return blueTeamMoves;
        }
        return redTeamMoves;
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
