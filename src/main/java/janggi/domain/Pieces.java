package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Pieces {

    private final List<Piece> pieces;

    public Pieces(final List<Piece> pieces) {
        this.pieces = new ArrayList<>(pieces);
    }

    public Piece findPieceByPositionAndTeam(Position position, Team team) {
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .filter(piece -> piece.isSameTeam(team))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 우리팀 기물이 없습니다."));
    }

    public void move(final Position position, final Piece piece) {
        if (hasPieceByPosition(position.x(), position.y())) {
            kill(position.x(), position.y());
        }
        piece.move(position);
    }

    private void kill(int x, int y) {
        pieces.remove(findPieceByPosition(x, y));
    }

    private boolean hasPieceByPosition(int x, int y) {
        Position position = new Position(x, y);
        return pieces.stream()
                .anyMatch(piece -> piece.isSamePosition(position));
    }

    private Piece findPieceByPosition(int x, int y) {
        Position position = new Position(x, y);
        return pieces.stream()
                .filter(piece -> piece.isSamePosition(position))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 없습니다."));
    }

    public List<Piece> getPieces() {
        return pieces;
    }
}
