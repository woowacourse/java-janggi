package domain.piece;

import domain.PieceType;

public class Sa implements Piece {

    private final Team team;

    public Sa(Team team) {
        this.team = team;
    }

    @Override
    public PieceType type() {
        return PieceType.SA;
    }
}
