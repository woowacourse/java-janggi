public class Game {
    private final Board board;
    private final Player choPlayer;
    private final Player hanPlayer;

    public Game(Board board, Player choPlayer, Player hanPlayer) {
        this.board = board;
        this.choPlayer = choPlayer;
        this.hanPlayer = hanPlayer;
    }

}
