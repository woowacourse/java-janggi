package janggi.data.dao.test;

import janggi.board.point.Point;
import janggi.data.dao.PieceDao;
import janggi.piece.Piece;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakePieceDao implements PieceDao {

    private final Map<Point, Piece> placedPieces = new HashMap<>();

    @Override
    public void save(Point point, Piece piece) {
        placedPieces.put(point, piece);
    }

    @Override
    public void move(Point from, Point to) {
        if (!placedPieces.containsKey(from)) {
            throw new IllegalArgumentException("기물 이동 정보를 저장하는 도중 오류가 발생했습니다.");
        }
        Piece piece = placedPieces.remove(from);
        placedPieces.put(to, piece);
    }

    @Override
    public void delete(Point point) {
        placedPieces.remove(point);
    }

    public Piece findByPoint(Point point) {
        return Optional.ofNullable(placedPieces.get(point))
                .orElseThrow(() -> new IllegalArgumentException("해당 위치에 기물이 존재하지 않습니다."));
    }
}
