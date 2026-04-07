package domain;

import domain.board.Board;
import domain.piece.Position;
import domain.player.Player;
import domain.player.Players;
import domain.player.Team;
import java.util.List;

public class JanggiGame {

    private final Board board;
    private final Players players;
    private Player currentPlayer;

    public JanggiGame(final Board board, final Player choPlayer, final Player hanPlayer) {
        this.board = board;
        this.players = new Players(List.of(choPlayer, hanPlayer));
        currentPlayer = choPlayer;
    }


    public void movePiece(final Position from, final Position to) {
        board.move(from, to);
        switchTurn();
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Board getBoard() {
        return board;
    }


    private void switchTurn() {
        final Team teamNextTurn = currentPlayer.getTeam().opponent();
        currentPlayer = players.findByTeam(teamNextTurn);
    }
}
