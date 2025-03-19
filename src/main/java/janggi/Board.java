package janggi;

import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Ma;
import janggi.piece.Po;
import janggi.piece.Sa;
import janggi.piece.Sang;
import janggi.point.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private final List<Movable> pieces;

    public Board(List<Movable> pieces) {
        this.pieces = pieces;
    }

    public static Board init() {
        List<Movable> pieces = new ArrayList<>();

        pieces.addAll(Gung.values());
        pieces.addAll(Sa.values());
        pieces.addAll(Ma.values());
        pieces.addAll(Sang.values());
        pieces.addAll(Cha.values());
        pieces.addAll(Po.values());
        pieces.addAll(Byeong.values());

        return new Board(pieces);
    }

    public List<Movable> getPieces() {
        return Collections.unmodifiableList(pieces);
    }

    public List<Movable> findPieceOnVerticalRoute(Point point) {
        return pieces.stream()
                .filter(piece -> point.isSameColumn(piece.getPoint()))
                .filter(piece -> !point.equals(piece.getPoint())) //TODO piece.hasSamePoint 로 리팩토링 가능
                .toList();
    }

    public List<Movable> findPieceOnHorizontalRoute(Point point) {
        return pieces.stream()
                .filter(piece -> point.isSameRow(piece.getPoint()))
                .filter(piece -> !point.equals(piece.getPoint())) //TODO piece.hasSamePoint 로 리팩토링 가능
                .toList();
    }

    public Movable findByPoint(Point point) {
        return pieces.stream()
                .filter(piece ->piece.getPoint().equals(point))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 좌표에 기물이 존재하지 않습니다."));
    }

    public boolean checkHurdles(Point startPoint, List<Point> route) {
        List<Point> piecePoints = pieces.stream()
                .map(Movable::getPoint).toList();

        List<Point> crashPoints = route.stream()
                .filter(piecePoints::contains)
                .toList();

        if (crashPoints.size() == 1
                && route.getLast().equals(crashPoints.getFirst())
        ) {
            TeamColor crashPieceColor = findByPoint(crashPoints.getFirst()).getColor();
            TeamColor movingPieceColor  = findByPoint(startPoint).getColor();
            return crashPieceColor == movingPieceColor;
        }

        return crashPoints.size() > 0;
    }
}
