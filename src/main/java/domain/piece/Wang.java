package domain.piece;

import domain.PieceType;

public class Wang implements Piece {

    private final Team team;

    public Wang(Team team) {
        this.team = team;
    }

    @Override
    public PieceType type() {
        return PieceType.WANG;
    }

    @Override
    public Team team() {
        return this.team;
    }
}
