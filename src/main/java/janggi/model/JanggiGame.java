package janggi.model;

import janggi.model.piece.Piece;
import java.util.Set;

public class JanggiGame {
    private final Board board;
    private final Turn turn;

    public JanggiGame(Board board, Turn turn) {
        this.board = board;
        this.turn = turn;
    }

    public boolean existCatchablePiece(Color ourColor, Position destination) {
        OccupiedPositions occupiedPositions = board.generateOccupiedPositions();
        return  occupiedPositions.existPosition(destination) && !occupiedPositions.existSameColor(destination, ourColor);
    }

    public void move(Position departure, Position destination) {
        Piece piece = board.findPieceByPositionAndColor(departure, turn.getCurrentTurn());
        OccupiedPositions occupied = board.generateOccupiedPositions();
        Set<Position> movablePositions = piece.calculateMovablePositions(departure, occupied);
        if (!movablePositions.contains(destination)) {
            throw new IllegalArgumentException("이동할 수 없는 위치입니다.");
        }
        board.move(departure, destination);
        turn.nextTurn();
    }

    public Board getBoard() {
        return board;
    }

    public Color getCurrentTurn() {
        return turn.getCurrentTurn();
    }

    public double calculateScore(Color color) {
        return board.calculateScore(color);
    }
}
