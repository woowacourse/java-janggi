package domain.piece;

public final class Guard extends SingleStepPiece {
    public Guard(Team team) {
        super(PieceType.GUARD, team);
    }

    @Override
    protected boolean mustStayInPalace() {
        return true;
    }
}
