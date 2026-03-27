package domain;

import domain.pieces.Piece;
import java.util.List;
import java.util.Map;

public class JanggiGame {
    private final Board board;
    private State state;

    public JanggiGame(Board board) {
        this.board = board;
        this.state = new ChoTurn();
    }

    public void play(Position start, Position end) {
        board.move(start, end);
        this.state = state.changeTurn();
    }

    public List<Position> getPiecesNowPosition(PieceType pieceType){
        return board.getPiecesNowPosition(state.getCountry(), pieceType);
    }

    public Country getCountry() {
        return state.getCountry();
    }

    public Map<Position, Piece> getBoard() {
        return board.getBoard();
    }
}
