package janggi.domain;

import janggi.domain.piece.Piece;
import java.util.Map;

public class Round {

    private final static double HANDICAP_POINTS = 1.5;

    private final Board board;
    private Side currentTurn = Side.CHO;

    public Round(Board board) {
        this.board = board;
    }

    public void commence(Position selectedPosition, Position targetPosition) {
        board.makeMove(currentTurn, selectedPosition, targetPosition);
        if (board.hasBothGenerals()) {
            changeTurn();
        }
    }

    private void changeTurn() {
        currentTurn = currentTurn.reverse();
    }

    public Map<Position, Piece> getCurrentPieces() {
        return board.getPieces();
    }

    public boolean hasBothGenerals() {
        return board.hasBothGenerals();
    }

    public Side getCurrentTurn() {
        return currentTurn;
    }

    public Map<Side, Double> getCurrentPoints() {
        double choPoints = board.getTotalPoints(Side.CHO);
        double hanPoints = board.getTotalPoints(Side.HAN) + HANDICAP_POINTS;
        return Map.of(Side.CHO, choPoints, Side.HAN, hanPoints);
    }
}
