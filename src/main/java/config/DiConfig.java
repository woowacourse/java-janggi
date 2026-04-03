package config;

import domain.board.Board;
import domain.janggigame.JanggiGame;
import domain.piece.Side;
import domain.player.Player;
import domain.players.Players;

public class DiConfig {

    private final Board board = new Board();

    public JanggiGame janggiGame() {
        return new JanggiGame(
                players(),
                board
        );
    }

    private Players players() {
        return new Players(choPlayer(), hanPlayer());
    }

    private Player choPlayer() {
        return new Player(Side.CHO);
    }

    private Player hanPlayer() {
        return new Player(Side.HAN);
    }
}
