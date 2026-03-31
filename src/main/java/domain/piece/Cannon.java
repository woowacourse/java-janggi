package domain.piece;

import domain.game.Team;
import domain.position.Position;
import java.util.List;

public class Cannon extends ActivePiece implements CannonRule {

    public Cannon(Team team) {
        super(team, PieceDefinition.PHO);
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
        return true;
    }

    @Override
    public boolean canJumpOver(Piece other) {
        return !other.isCannon();
    }

    @Override
    public boolean canCaptureDest(Piece dest) {
        return !dest.isCannon();
    }

    @Override
    public int requiredJumpCount() {
        return 1;
    }
}
