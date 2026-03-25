package janggi.domain.piece;

public class Advisor extends Piece {

    public Advisor(Team team) {
        super(team);
    }


    @Override
    public PieceType pieceType() {
        return PieceType.ADVISOR;
    }
}
