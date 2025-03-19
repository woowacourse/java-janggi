package model;

import java.util.ArrayList;
import java.util.List;

import model.piece.Palace;
import model.piece.Piece;

public class Board {

    private static final int WIDTH_SIZE = 9;
    private static final int HEIGHT_SIZE = 10;

    private final List<Piece> pieces;

    public Board(List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public boolean isInboard(Position position) {
        return position.x() < WIDTH_SIZE && position.x() >= 0
            && position.y() < HEIGHT_SIZE && position.y() >= 0;
    }

    public boolean hasPieceOn(Position position) {
        return pieces.stream()
            .anyMatch(piece -> piece.onPosition(position));
    }

    public Piece get(Position position) {
        return pieces.stream()
            .filter(piece -> piece.onPosition(position))
            .findAny()
            .orElseThrow(IllegalArgumentException::new);
    }

    public void take(Piece target) {
        pieces.remove(target);
    }

    public Team getWinnerIfGameOver() {
        List<Piece> palaces = pieces.stream()
            .filter(piece -> piece instanceof Palace)
            .toList();
        if (palaces.size() == 1) {
            return palaces.getFirst().getTeam();
        }
        return null;
    }
}
