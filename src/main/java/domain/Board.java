package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final Map<Position, Piece> board;

    public Board(Map<Position, Piece> board) {
        this.board = board;
    }

    public List<Position> getPossibleDestinations(Position position) {
        Piece piece = board.get(position);
        List<Position> positions = piece.getAllPosition(position);
        Map<Position, Piece> map = makePiecesFromPositions(positions);
        return piece.getPossibleDestinations(position, map);
    }

    private Map<Position, Piece> makePiecesFromPositions(List<Position> positions) {
        Map<Position, Piece> pieces = new HashMap<>();

        for (Position position : positions) {
            if (board.containsKey(position)) {
                pieces.put(position, board.get(position));
            }
        }

        return pieces;
    }

    public void movePiece(Position from, Position to) {
        board.put(to, board.remove(from));
    }

    public boolean isGameOver() {
        return board.values().stream()
                .filter(piece -> piece instanceof General)
                .count() < 2;
    }

    public Map<Position, Piece> getBoard() {
        return board;
    }
}
