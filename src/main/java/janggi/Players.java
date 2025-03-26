package janggi;

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

    public static Players create(Turn turn) {
        Player cho = Player.from(Team.CHO);
        Player han = Player.from(Team.HAN);

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

    public Player getPlayer(Team team) {
        if (team.isCho()) {
            return cho;
        }
        return han;
    }

    public Score getScore(Team team) {
        if (team.isCho()) {
            return cho.getScore();
        }
        return han.getScore();
    }
}
