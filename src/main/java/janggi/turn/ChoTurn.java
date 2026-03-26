package janggi.turn;

public class ChoTurn implements Turn{
    @Override
    public Turn play() {
        return new HanTurn();
    }

    @Override
    public boolean isGameOver() {
        return false;
    }
}
