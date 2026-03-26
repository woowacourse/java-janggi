package janggi.turn;

public class GameOver implements Turn {
    @Override
    public Turn play() {
        throw new IllegalStateException("게임 종료 후 턴을 수행할 수 없습니다.");
    }

    @Override
    public boolean isGameOver() {
        return true;
    }
}
