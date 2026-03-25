package domain;

public class JanggiGame {
    private final Board board;
    private State state;

    public JanggiGame(Board board) {
        this.board = board;
        this.state = new ChoTurn();
    }

    public void play() {
        // TODO : 보드 한턴 진행
        this.state = state.changeTurn();
    }

    public Country getCountry() {
        return state.getCountry();
    }
}
