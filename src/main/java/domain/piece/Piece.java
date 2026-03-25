package domain.piece;

import domain.player.Team;
import domain.position.Path;
import domain.position.Position;

public abstract class Piece {

    protected final Team team;

    public Piece(Team team) {
        this.team = team;
    }


    //경로 계산.
    public abstract Path getPath(Position src, Position dest);
}
