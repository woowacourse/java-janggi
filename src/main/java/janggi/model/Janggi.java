package janggi.model;

import janggi.model.board.Board;
import janggi.model.position.absolute.Position;
import janggi.model.turn.GameOver;
import janggi.model.turn.Turn;
import janggi.model.turn.playing.ChoTurn;
import janggi.model.turn.playing.HanTurn;

public class Janggi {

    private final Turn turn;
    private final ScorePolicy scorePolicy;

    private Janggi(Turn turn, ScorePolicy scorePolicy) {
        this.turn = turn;
        this.scorePolicy = scorePolicy;
    }

    public static Janggi of(Board board) {
        return new Janggi(
                new ChoTurn(board),
                new ScorePolicy()
        );
    }

    public static Janggi continueFrom(Turn turn) {
        Turn nextTurn = new ChoTurn(turn.board());

        if (turn.isChoTurn()) {
            nextTurn = new HanTurn(nextTurn.board());
        }

        return new Janggi(nextTurn, new ScorePolicy());
    }

    public Janggi play(Position from, Position to) {
        return new Janggi(turn.play(from, to), scorePolicy);
    }

    public boolean isGameOver() {
        return turn.isGameOver();
    }

    public Board getBoard() {
        return turn.board();
    }

    public Team getCurrentTeam() {
        if (turn.isChoTurn()) {
            return Team.CHO;
        }

        return Team.HAN;
    }

    public Team getWinner() {
        return turn.getWinner(scorePolicy);
    }

    public Janggi draw() {
        return new Janggi(new GameOver(getBoard()), scorePolicy);
    }
}
