package domain;

import domain.constant.Country;
import domain.constant.PieceType;
import domain.constant.Turn;
import domain.state.GameState;
import domain.state.Playing;
import java.util.List;

public class JanggiGame {
    private static final double HAN_BONUS_SCORE = 1.5;

    private final Board board;
    private GameState gameState;

    public JanggiGame(Board board) {
        this.board = board;
        this.gameState = new Playing(Turn.CHO);
    }

    public JanggiGame(Board board, Country country) {
        this.board = board;
        this.gameState = new Playing(Turn.from(country));
    }

    public void play(Position start, Position end) {
        this.gameState = gameState.move(board, start, end);
    }

    public List<Position> getPiecesNowPosition(PieceType pieceType){
        return board.getPiecesNowPosition(gameState.getTurn(), pieceType);
    }

    public Country getCountry() {
        return gameState.getTurn();
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
        return gameState.isFinished();
    }

    public String getWinnerCountry() {
        return gameState.getWinner().getName();
    }

    public boolean isEmptyPosition(Position end) {
        return board.checkEndPosition(end);
    }
}
