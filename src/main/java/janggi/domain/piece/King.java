package janggi.domain.piece;

public class King extends Piece {

    public King(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.KING;
    }
}
