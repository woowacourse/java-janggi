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
    public void validateJumpOver(Piece other) {
        if (other.isCannon()) {
            throw new IllegalArgumentException("포는 포를 넘지 못합니다.");
        }
    }

    @Override
    public void validateCaptureDest(Piece dest) {
        if (dest.isCannon()) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없습니다.");
        }
    }

    @Override
    public void validateJumpCount(int count) {
        if (count != 1) {
            throw new IllegalArgumentException("포가 넘을 수 있는 기물의 개수는 하나입니다.");
        }
    }
}
