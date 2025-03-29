package janggi.domain.board;

import janggi.domain.piece.Dynasty;
import janggi.domain.piece.EmptyPiece;
import janggi.domain.piece.Piece;
import janggi.domain.piece.PiecesOnPath;
import janggi.domain.piece.Point;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JanggiBoard {

    private final Map<Point, Piece> pieces;

    public JanggiBoard(Map<Point, Piece> pieces) {
        this.pieces = new HashMap<>(pieces);
    }

    public static JanggiBoard of(BoardSetUp hanBoardSetUp, BoardSetUp chuBoardSetUp) {
        Map<Point, Piece> pieces = new HashMap<>();
        pieces.putAll(hanBoardSetUp.pieces());
        pieces.putAll(chuBoardSetUp.pieces());
        return new JanggiBoard(pieces);
    }

    public void move(Dynasty dynasty, Point from, Point to) {
        validateExistPiece(from);

        Piece piece = pieces.get(from);
        validateMovablePiece(dynasty, piece);

        List<Point> movePath = piece.movePath(from, to);
        validateCanMoveByPath(piece, movePath);
        pieces.remove(from);
        pieces.put(to, piece);
    }

    public double dynastyScore(Dynasty dynasty) {
        int totalScore = pieces.values().stream()
                .filter(piece -> piece.isDynasty(dynasty))
                .mapToInt(Piece::score)
                .sum();
        return dynasty.score(totalScore);
    }

    public boolean isDeadKing(Dynasty dynasty) {
        return pieces.values().stream()
                .noneMatch(piece -> piece.isDynastyKing(dynasty));
    }

    private Piece findPiece(Point point) {
        return pieces.getOrDefault(point, new EmptyPiece());
    }

    private void validateExistPiece(Point point) {
        if (!pieces.containsKey(point)) {
            throw new IllegalArgumentException("시작 위치에 기물이 존재하지 않습니다.");
        }
    }

    private void validateMovablePiece(Dynasty dynasty, Piece piece) {
        if (!piece.isDynasty(dynasty)) {
            throw new IllegalArgumentException("자신의 나라 기물만 움직일 수 있습니다.");
        }
    }

    private void validateCanMoveByPath(Piece piece, List<Point> movePath) {
        if (!piece.canMove(findPiecesOnPath(movePath))) {
            throw new IllegalArgumentException("해당 위치로 움직일 수 없습니다.");
        }
    }

    private PiecesOnPath findPiecesOnPath(List<Point> movePath) {
        List<Piece> piecesOnPth = movePath.stream()
                .map(this::findPiece)
                .toList();
        return new PiecesOnPath(piecesOnPth);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JanggiBoard that = (JanggiBoard) o;
        return Objects.equals(pieces, that.pieces);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pieces);
    }

    public Map<Point, Piece> getPieces() {
        return Collections.unmodifiableMap(pieces);
    }
}
