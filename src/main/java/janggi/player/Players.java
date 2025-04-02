package janggi.player;

import janggi.board.Board;
import janggi.piece.Pieces;

public class Players {

    private final Player cho;
    private final Player han;
    private final Turn turn;

    private Players(final Player cho, final Player han, final Turn turn) {
        this.cho = cho;
        this.han = han;
        this.turn = turn;
    }

    public static Players create(final Turn turn) {
        final Player cho = Player.from(Team.CHO);
        final Player han = Player.from(Team.HAN);

        return new Players(cho, han, turn);
    }

    public static Players of(final Pieces pieces, final Turn turn, final Score choScore, final Score hanScore) {
        final Player cho = Player.of(Team.CHO, pieces.getByTeam(Team.CHO), choScore);
        final Player han = Player.of(Team.HAN, pieces.getByTeam(Team.HAN), hanScore);

        return new Players(cho, han, turn);
    }

    public Board createBoard() {
        return Board.from(getBothPieces());
    }

    public Player getCurrentPlayer() {
        if (turn.getCurrentTeam().isCho()) {
            return cho;
        }
        return han;
    }

    public Pieces getBothPieces() {
        return cho.getPieces().addAll(han.getPieces());
    }

    public Player getPlayer(final Team team) {
        if (team.isCho()) {
            return cho;
        }
        return han;
    }

    public boolean isWin(final Player player) {
        return player.isWin();
    }

    public Score getScore(final Team team) {
        if (team.isCho()) {
            return cho.getScore();
        }
        return han.getScore();
    }

    public Turn getTurn() {
        return turn;
    }
}
