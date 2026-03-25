package janggi.domain.piece;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(team);
    }


    @Override
    public PieceType pieceType() {
        return PieceType.CANNON;
    }
}
