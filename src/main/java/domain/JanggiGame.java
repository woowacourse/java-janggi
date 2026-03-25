package domain;

public class JanggiGame {
    private final Board board;
    private State state;

    public JanggiGame(Board board) {
        this.board = board;
        this.state = new ChoTurn();
    }

    public void play() {
        // 기물 이동
        state.changeTurn();
    }
}
