package domain.piece.state;

public class MovedElephant extends NonContinuousPiece {
    @Override
    public PieceState updateState() {
        return new MovedElephant();
    }
}
