package model;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

public class Board {
    private final Map<Point, String> pieces = new HashMap<>();

    public void move(Point targetPoint, Point destinationPoint) {
        if (!pieces.containsKey(targetPoint)) {
            throw new IllegalArgumentException("기물이 존재하지 않는 위치입니다.");
        }
        String pieceName = pieces.get(targetPoint);
        pieces.put(destinationPoint, pieceName);
        pieces.remove(targetPoint);
    }

    public boolean isExist(Point point) {
        return pieces.containsKey(point);
    }

    public void putPiece(Point point, String pieceType) {
        pieces.put(point, pieceType);
    }
}
