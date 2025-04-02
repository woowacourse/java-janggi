package janggi.player;

import janggi.piece.Cannon;
import janggi.piece.Chariot;
import janggi.piece.Elephant;
import janggi.piece.General;
import janggi.piece.Guard;
import janggi.piece.Horse;
import janggi.piece.Piece;
import janggi.piece.Pieces;
import janggi.piece.Soldier;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private final Team team;
    private final Pieces pieces;
    private Score score;

    public Player(final Team team, final Pieces pieces, final Score score) {
        this.team = team;
        this.pieces = pieces;
        this.score = score;
    }

    public static Player from(final Team team) {
        final List<Piece> pieces = new ArrayList<>();

        pieces.add(General.defaultOf(team));
        pieces.addAll(Guard.defaultsOf(team));
        pieces.addAll(Soldier.defaultsOf(team));
        pieces.addAll(Horse.defaultsOf(team));
        pieces.addAll(Elephant.defaultsOf(team));
        pieces.addAll(Chariot.defaultsOf(team));
        pieces.addAll(Cannon.defaultsOf(team));

        return new Player(team, new Pieces(pieces), new Score(0));
    }

    public static Player of(final Team team, final Pieces pieces, final Score score) {
        return new Player(team, pieces, score);
    }

    public void addScore(final Score score) {
        this.score = this.score.add(score);
    }

    public boolean isWin() {
        return score.isGreaterThan(Score.win());
    }

    public Team getTeam() {
        return team;
    }

    public Pieces getPieces() {
        return pieces;
    }

    public Score getScore() {
        return score;
    }
}
