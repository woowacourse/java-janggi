package domain.state;

public class HanSide extends Running {

    public HanSide() {
        super(Side.HAN);
    }

    @Override
    protected State changeTurn() {
        return new ChuSide();
    }
}
