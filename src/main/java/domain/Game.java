package domain;

public class Game {

    private final String name;
    private final GameStatus status;
    private final Board board;

    public Game(final String name, final GameStatus status, final Board board) {
        this.name = name;
        this.status = status;
        this.board = board;
    }

    public String getName() {
        return name;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Board getBoard() {
        return board;
    }
}
