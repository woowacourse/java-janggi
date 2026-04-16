package domain.board;

import domain.piece.Empty;
import domain.piece.King;
import domain.piece.Piece;
import domain.piece.Side;
import domain.vo.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board {

    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public void movePiece(Position sourcePosition, Position targetPosition) {
        board.put(targetPosition, board.get(sourcePosition));
        board.put(sourcePosition, new Empty());
    }

    public Piece getPiece(Position position) {
        return board.get(position);
    }

    public List<Piece> findPiecesOnRoute(List<Position> route, Position targetPosition) {
        List<Piece> pieces = new ArrayList<>();
        for (Position position : route) {
            if (!position.equals(targetPosition)) {
                pieces.add(board.get(position));
            }
        }
        return pieces;
    }

    public boolean hasKing(Side side) {
        for (Piece piece : board.values()) {
            if (piece instanceof King && piece.isSameSide(side)) {
                return true;
            }
        }
        return false;
    }

    public double calculateTotalScore(Side side) {
        double totalScore = 0;
        if (side == Side.HAN) {
            totalScore += 1.5;
        }
        for (Piece piece : board.values()) {
            if (piece.isSameSide(side)) {
                totalScore += piece.getScore();
            }
        }
        return totalScore;
    }

    public Map<Position, Piece> getBoard() {
        return Map.copyOf(board);
    }
}
