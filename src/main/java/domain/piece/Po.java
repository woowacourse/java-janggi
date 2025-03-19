package domain.piece;

import domain.PieceType;

public class Po implements Piece {

    private final Team team;

    public Po(Team team) {
        this.team = team;
    }

    @Override
    public PieceType type() {
        return PieceType.PO;
    }
}
