package domain.game;

public class JanggiGame {
    private GameState state = new Start();

    public boolean isEnd() {
        return state.isEnd();
    }
}
