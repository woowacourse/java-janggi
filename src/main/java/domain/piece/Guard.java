package domain.piece;

import domain.game.Team;

public class Guard extends PalacePiece {
    public Guard(Team team) {
        super(team, PieceDefinition.SA);
    }
}
