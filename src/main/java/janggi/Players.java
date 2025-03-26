package janggi;

import janggi.piece.Pieces;

public class Players {

    private final Player cho;
    private final Player han;

    private Players(final Player cho, final Player han) {
        this.cho = cho;
        this.han = han;
    }

    public static Players create() {
        Player cho = Player.from(Team.CHO);
        Player han = Player.from(Team.HAN);

        return new Players(cho, han);
    }

    public Board createBoard() {
        return Board.from(getBothPieces());
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
