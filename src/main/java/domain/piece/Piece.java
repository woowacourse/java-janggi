package domain.piece;

import domain.player.Team;
import domain.position.Position;
import java.util.List;

public abstract class Piece {

    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    //이동이 가능한지 안되는지 판단.
    protected boolean canMoveByMovingRule(Position src, Position dest) {
        return getRawPositions(src).contains(dest);
    }

    //이동규칙에 따라 포지션 계산 -> List<List<>> ->
    protected abstract List<Position> getRawPositions(Position src);

    //경로 계산.
    public abstract List<Position> getPath(Position src, Position dest);


}
