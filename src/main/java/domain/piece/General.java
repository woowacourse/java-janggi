package domain.piece;

public final class General extends SingleStepPiece {
    public General(Team team) {
        super(PieceType.GENERAL, team);
    }

    @Override
    protected boolean mustStayInPalace() {
        return true;
    }
}
