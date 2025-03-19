package domain.piece;

import domain.PieceType;

public class Cha implements Piece {

    private final Team team;

    public Cha(Team team) {
        this.team = team;
    }

    @Override
    public PieceType type() {
        return PieceType.CHA;
    }

    @Override
    public boolean hasTeam(Team team) {
        return this.team == team;
    }
}
