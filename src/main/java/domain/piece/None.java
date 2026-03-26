package domain.piece;

import domain.player.Team;
import domain.position.Path;
import domain.position.Position;

public class None extends Piece{

    public None(Team team) {
        super(team, PieceType.NONE, null, null);
    }

}
