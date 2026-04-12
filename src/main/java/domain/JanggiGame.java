package domain;

import domain.enums.Country;
import domain.enums.PieceType;
import domain.state.ChoTurn;
import domain.state.State;

import java.util.List;

public class JanggiGame {
    private final Board board;
    private State state;

    public JanggiGame(Board board) {
        this.board = board;
        this.state = new ChoTurn();
    }

    public JanggiGame(Board board, State state) {
        this.board = board;
        this.state = state;
    }

    public void play(Position start, Position end) {
        PieceType killPieceType = board.move(start, end);
        if (killPieceType==PieceType.JANG){
            this.state = state.exitGame();
            return;
        }
        this.state = state.changeTurn();
    }

    public boolean isGameOver() {
        return state.isGameOver();
    }

    public Country calculateWinner(){
        boolean isChoKingAlive = board.isKingAlive(Country.CHO);
        boolean isHanKingAlive = board.isKingAlive(Country.HAN);
        if (!isChoKingAlive && isHanKingAlive){
            return Country.HAN;
        }
        if (isChoKingAlive && !isHanKingAlive){
            return Country.CHO;
        }
        return compareScore();
    }

    public double calculateScore(Country country) {
        if (country==Country.HAN){
            return board.calculateScore(country)+1.5;
        }
        return board.calculateScore(country);
    }

    private Country compareScore() {
        double choScore = calculateScore(Country.CHO);
        double hanScore = calculateScore(Country.HAN);
        if (choScore > hanScore){
            return Country.CHO;
        }
        if (choScore < hanScore){
            return Country.HAN;
        }
        return Country.NONE;
    }

    public List<Position> getPiecesNowPosition(PieceType pieceType){
        return board.getPiecesNowPosition(state.getCountry(), pieceType);
    }

    public Country getCountry() {
        return state.getCountry();
    }

    public String getStateValue() {
        return state.getValue();
    }

    public Board getBoard() {
        return board;
    }
}
