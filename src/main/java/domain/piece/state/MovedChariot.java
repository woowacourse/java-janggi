package domain.piece.state;

public class MovedChariot extends ContinuousPiece {
    @Override
    public PieceState updateState() {
        return new MovedChariot();
    }
}
