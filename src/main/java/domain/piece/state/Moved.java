package domain.piece.state;

public abstract class Moved implements PieceState {
    @Override
    public PieceState captured() {
        return new Captured();
    }
}
