package domain.piece.state;

public class MovedSoldierJol extends NonContinuousPiece {
    @Override
    public PieceState updateState() {
        return new MovedSoldierJol();
    }
}
