package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;

public class StraightMovePiece extends ActivePiece {

    private final boolean cannon;

    public StraightMovePiece(Team team, PieceDefinition type, boolean cannon) {
        super(team, type);
        this.cannon = cannon;
    }

    public static StraightMovePiece chariot(Team team) {
        return new StraightMovePiece(team, PieceDefinition.CHA, false);
    }

    public static StraightMovePiece cannon(Team team) {
        return new StraightMovePiece(team, PieceDefinition.PHO, true);
    }

    @Override
    public boolean canMove(Position source, Position target) {
        return source.isSameCol(target) || source.isSameRow(target);
    }

    @Override
    public List<Position> searchRoute(Position source, Position target) {
        if (source.isSameCol(target)) {
            return source.makeRowStraightRoute(target);
        }
        return source.makeColStraightRoute(target);
    }

    @Override
    public boolean isCannon() {
        return cannon;
    }
}
