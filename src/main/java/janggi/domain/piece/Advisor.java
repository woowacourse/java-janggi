package janggi.domain.piece;

public class Advisor extends Piece {

    public Advisor(Team team) {
        super(team);
    }

    @Override
    public String displayName() {
        return "사";
    }

    @Override
    public PieceType pieceType() {
        return PieceType.ADVISOR;
    }
}
