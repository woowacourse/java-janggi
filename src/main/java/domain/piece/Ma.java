package domain.piece;

import domain.PieceType;

public class Ma implements Piece {

    private final Team team;

    public Ma(Team team) {
        this.team = team;
    }

    @Override
    public PieceType type() {
        return PieceType.MA;
    }

    @Override
    public Team team() {
        return this.team;
    }
}
