import domain.board.Board;
import domain.place.piece.Side;
import domain.player.Player;
import domain.player.Players;
import domain.position.Position;
import java.util.List;

public class Janggi {

    private final Players players;
    private final Board board;
    private Player currentPlayer;

    public Janggi(Players players, Board board) {
        this.players = players;
        this.board = board;
        this.currentPlayer = players.getPlayerBySide(Side.CHO);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<List<String>> getBoardFormat() {
        return board.getFormatBoard();
    }

    public void move(Position from, Position to) {
        board.move(from, to, currentPlayer.getSide());
        changeTurn();
    }

    private void changeTurn() {
        if (currentPlayer.getSide() == Side.CHO) {
            currentPlayer = players.getPlayerBySide(Side.HAN);
            return;
        }
        currentPlayer = players.getPlayerBySide(Side.CHO);
    }
}
