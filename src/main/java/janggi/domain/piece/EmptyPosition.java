package janggi.domain.piece;

public class EmptyPosition extends Piece {

    public EmptyPosition(Team team) {
        super(team);
    }


    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.EMPTY;
    }
}
