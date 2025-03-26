package domain.piece.state;

public class MovedHorse extends NonContinuousPiece {
    @Override
    public PieceState updateState() {
        return new MovedHorse();
    }
}
