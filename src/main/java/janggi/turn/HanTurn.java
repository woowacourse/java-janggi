package janggi.turn;

public class HanTurn implements Turn{

    @Override
    public Turn play() {
        return new ChoTurn();
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
