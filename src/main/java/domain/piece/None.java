package domain.piece;

import domain.player.Team;

public class None extends Piece {

    public None(Team team) {
        super(team, PieceType.NONE, null, null);
    }
}
