package model;

import model.board.Board;
import model.board.Country;
import model.board.Status;
import model.move.Move;

public class JanggiGame {
    private static final int GENERAL_COUNT = 2;
    private static final int THREE_FOLD_REPETITION = 3;
    private final int id;
    private final Board board;
    private Country turn;
    private Status status;

    public JanggiGame(int id, Board board, Country turn, Status status) {
        this.id = id;
        this.board = board;
        this.turn = turn;
        this.status = status;
    }

    public void nextTurn() {
        this.turn = turn.convertCountry();
    }

    public boolean isProgressing() {
        return !endCondition() && !isDraw();
    }

    public Country scoreWinnerCountry() {
        double choScore = board.sumScore(Country.CHO);
        double hanScore = board.sumScore(Country.HAN);
        if (choScore > hanScore) {
            return Country.CHO;
        }

        return Country.HAN;
    }

    public void move(Move move, int repetitionCount) {
        board.move(move);
        checkDraw(repetitionCount);
    }

    public int id() {
        return id;
    }

    public Board board() {
        return board;
    }

    public Country turn() {
        return turn;
    }

    public Status status() {
        return status;
    }

    public void checkFinished() {
        this.status = Status.FINISHED;
    }

    public boolean isDraw() {
        return this.status == Status.DRAW;
    }

    public boolean isFinished() {
        return this.status == Status.FINISHED;
    }

    private boolean endCondition() {
        return board.countGeneral() < GENERAL_COUNT;
    }

    private void checkDraw(int repetitionCount) {
        if (repetitionCount >= THREE_FOLD_REPETITION) {
            this.status = Status.DRAW;
        }
    }
}