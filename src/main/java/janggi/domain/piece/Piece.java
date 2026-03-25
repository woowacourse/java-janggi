package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Space;
import janggi.domain.Team;
import java.util.List;

public abstract class Piece implements Space {

    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public abstract void validateMove(Position from, Position to);

    public void validateArrival(Space space) {
        if (space.isBlank()) {
            return;
        }

        Piece piece = (Piece) space;
        if (!team.equals(piece.team)) {
            return;
        }

        throw new IllegalArgumentException("이동하려는 위치에 같은 팀의 말이 존재합니다.");
    }

    public void validateRoutes(List<Piece> pieces){
        if (!pieces.isEmpty()){
            throw new IllegalArgumentException("이동 경로 사이에 다른 말이 있으면 안됩니다.");
        }
    }

    @Override
    public boolean isBlank() {
        return false;
    }
}
