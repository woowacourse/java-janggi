package janggi.model.board;

import janggi.model.board.position.Position;
import janggi.model.piece.Piece;
import java.util.List;
import java.util.Map;

public class PositionPath {

    private final List<Position> path;

    public PositionPath(List<Position> path) {
        this.path = path;
    }

    public List<Piece> findPiecesOn(Map<Position, Piece> board) {
        return path.stream()
                .filter(board::containsKey)
                .map(board::get)
                .toList();
    }
}
