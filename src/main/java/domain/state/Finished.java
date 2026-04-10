package domain.state;

public class Finished implements GameState {

    private final Side winner;

    public Finished(Side winner) {
        this.winner = winner;
    }

    @Override
    public GameState endGame() {
        throw new IllegalStateException("이미 종료된 게임입니다.");
    }

    @Override
    public GameState nextTurn() {
        throw new IllegalStateException("이미 종료된 게임입니다.");
    }

    @Override
    public Side getSide() {
        return winner;
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
