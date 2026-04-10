package domain.state;

public class ChuSide extends Running {

    public ChuSide() {
        super(Side.CHU);
    }

    @Override
    protected GameState changeTurn() {
        return new HanSide();
    }
}
