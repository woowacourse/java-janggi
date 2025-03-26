package domain.piece.state;

public class MovedSoldierByeong extends NonContinuousPiece {
    @Override
    public PieceState updateState() {
        return new MovedSoldierByeong();
    }
}
