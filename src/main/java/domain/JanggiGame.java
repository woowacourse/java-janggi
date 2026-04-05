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

    public void play(Position start, Position end) {
        PieceType killPieceType = board.move(start, end);
        if (killPieceType==PieceType.JANG){
            this.state = state.exitGame();
            return;
        }
        this.state = state.changeTurn();
    }

    public boolean isGameOver() {
        return state.getCountry()==Country.NONE;
    }

    public List<Position> getPiecesNowPosition(PieceType pieceType){
        return board.getPiecesNowPosition(state.getCountry(), pieceType);
    }

    public Country getCountry() {
        return state.getCountry();
    }
}
