package domain;

import domain.constant.Country;
import domain.constant.PieceType;
import domain.state.ChoTurn;
import domain.state.Finished;
import domain.state.State;
import domain.state.StateFactory;
import java.util.List;

public class JanggiGame {
    private static final double HAN_BONUS_SCORE = 1.5;

    private final Board board;
    private State state;

    public JanggiGame(Board board) {
        this.board = board;
        this.state = new ChoTurn();
    }

    public JanggiGame(Board board, Country country) {
        this.board = board;
        this.state = StateFactory.from(country);
    }

    public void play(Position start, Position end) {
        if (board.move(start, end)) {
            this.state = new Finished(state.getCountry());
            return;
        }
        this.state = state.changeTurn();
    }

    public List<Position> getPiecesNowPosition(PieceType pieceType){
        return board.getPiecesNowPosition(state.getCountry(), pieceType);
    }

    public Country getCountry() {
        return state.getCountry();
    }

    public double calculateScore() {
        Country country = getCountry();
        if (country.equals(Country.CHO)) {
            return calculateChoScore();
        }
        return calculateHanScore();
    }

    public double calculateChoScore() {
        return board.calculateScore(Country.CHO);
    }

    public double calculateHanScore() {
        return board.calculateScore(Country.HAN) + HAN_BONUS_SCORE;
    }

    public boolean isFinished() {
        return state instanceof Finished;
    }

    public String getWinnerCountry() {
        return state.getCountry().getName();
    }

    public boolean isEmptyPosition(Position end) {
        return board.checkEndPosition(end);
    }
}
