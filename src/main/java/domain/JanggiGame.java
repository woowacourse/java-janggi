package domain;

import domain.board.Board;
import domain.piece.Position;
import domain.player.Player;
import java.util.List;

public class JanggiGame {

    private final Board board;
    private final List<Player> players;

    private int turnIndex = 0;

    public JanggiGame(final Board board, final Player choPlayer, final Player hanPlayer) {
        this.board = board;
        this.players = List.of(choPlayer, hanPlayer);
    }


    public void movePiece(final Position from, final Position to) {
        board.move(from, to);
        switchTurn();
    }


    public Player getCurrentPlayer() {
        return players.get(turnIndex);
    }

    public Board getBoard() {
        return board;
    }


    private void switchTurn() {
        turnIndex = (turnIndex + 1) % players.size();
    }
}
