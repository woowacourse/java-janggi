package domain;

public class HanTurn implements State{
    @Override
    public State changeTurn() {
        return new ChoTurn();
    }
}
