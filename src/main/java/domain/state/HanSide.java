package domain.state;

public class HanSide extends Running {

    public HanSide() {
        super(Side.HAN);
    }

    @Override
    protected GameState changeTurn() {
        return new ChuSide();
    }
}
