package domain.piece;

import domain.PieceType;

public class Sang implements Piece {

    private final Team team;

    public Sang(Team team) {
        this.team = team;
    }

    @Override
    public PieceType type() {
        return PieceType.SANG;
    }

    @Override
    public Team team() {
        return this.team;
    }
}
