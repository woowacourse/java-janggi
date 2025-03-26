package janggi.domain;

import janggi.domain.piece.Piece;
import java.util.Map;

public class Round {

    private final Board board;
    private Side currentTurn = Side.CHO;

    public Round(Board board) {
        this.board = board;
    }

    public void commence(Position selectedPosition, Position targetPosition) {
        board.makeMove(currentTurn, selectedPosition, targetPosition);
        changeTurn();
    }

    private void changeTurn() {
        currentTurn = currentTurn.reverse();
    }

    public Map<Position, Piece> getCurrentPieces() {
        return board.getPieces();
    }

    public boolean hasGeneral() {
        return board.hasGeneral();
    }

    public Side getCurrentTurn() {
        return currentTurn;
    }
}
