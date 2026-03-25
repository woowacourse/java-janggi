package janggi.domain.piece;

public class Tank extends Piece {

    public Tank(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.TANK;
    }
}
