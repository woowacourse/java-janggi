package janggi.domain.piece;

public class Elephant extends Piece {

    public Elephant(Team team) {
        super(team);
    }


    @Override
    public PieceType pieceType() {
        return PieceType.ELEPHANT;
    }
}
