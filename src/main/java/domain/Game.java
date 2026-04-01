package domain;

public class Game {

    private final Turn turn;
    private final Board board;

    private Game(final Board board) {
        this.turn = Turn.of();
        this.board = board;
    }

    public static Game of(final Board board) {
        return new Game(board);
    }

    public Team nextTurn() {
        return this.turn.change();
    }

    public String getTurnName() {
        return turn.getTeamName();
    }

    public Team getTeam() {
        return turn.getTeam();
    }
}
