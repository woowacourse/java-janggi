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

    public void checkTurn(Team team) {
        if (team != turn.getTeam()) {
            throw new IllegalArgumentException("[ERROR] 해당 기물은 상대편 기물이기 떄문에 움직일 수 없습니다.");
        }
    }

    public void nextTurn() {
        this.turn.change();
    }

    public String getTurnName() {
        return turn.getTeamName();
    }

    public Team getTeam() {
        return turn.getTeam();
    }

    public Board getBoard() {
        return board;
    }
}
