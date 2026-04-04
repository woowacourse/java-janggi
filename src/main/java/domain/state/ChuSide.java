package domain.state;

public class ChuSide extends Running {

    public ChuSide() {
        super(Side.CHU);
    }

    @Override
    protected State changeTurn() {
        return new HanSide();
    }
}
