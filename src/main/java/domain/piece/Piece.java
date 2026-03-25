package domain.piece;

import domain.player.Team;
import domain.position.Position;
import java.util.List;

public abstract class Piece {

    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    //RawMove를 계산해서 dest가 있는지 없는지 판단.
    public boolean canMove(Position src, Position dest) {
        return getRawPositions(src).contains(dest);
    }

    //이동규칙에 따라 포지션 계산
    protected abstract List<Position> getRawPositions(Position src);

    //경로 계산.
    protected abstract List<Position> getPaths(Position src, Position dest);


}
