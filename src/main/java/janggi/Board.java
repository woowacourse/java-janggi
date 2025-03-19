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
    private final List<Movable> runningPieces;
    private final List<Movable> attackedPieces;

    public Board(List<Movable> runningPieces) {
        this.runningPieces = runningPieces;
        this.attackedPieces = new ArrayList<>();
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

    public List<Movable> getRunningPieces() {
        return Collections.unmodifiableList(runningPieces);
    }

    public List<Movable> findPieceOnVerticalRoute(Point point) {
        return runningPieces.stream()
                .filter(piece -> point.isSameColumn(piece.getPoint()))
                .filter(piece -> !point.equals(piece.getPoint()))
                .toList();
    }

    public List<Movable> findPieceOnHorizontalRoute(Point point) {
        return runningPieces.stream()
                .filter(piece -> point.isSameRow(piece.getPoint()))
                .filter(piece -> !point.equals(piece.getPoint()))
                .toList();
    }

    public Movable findByPoint(Point point) {
        return runningPieces.stream()
                .filter(piece ->piece.getPoint().equals(point))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 좌표에 기물이 존재하지 않습니다."));
    }

    public boolean hasAttackedPiece(Point point) {
        return runningPieces.stream()
                .anyMatch(piece -> piece.getPoint().equals(point));
    }

    public boolean checkHurdles(Point startPoint, List<Point> route) {
        List<Point> piecePoints = runningPieces.stream()
                .map(Movable::getPoint).toList();

        List<Point> crashPoints = route.stream()
                .filter(piecePoints::contains)
                .toList();

        if (crashPoints.size() == 1
                && route.getLast().equals(crashPoints.getFirst())
        ) {
            Movable crashPiece = findByPoint(crashPoints.getFirst());
            Movable movingPiece = findByPoint(startPoint);
            TeamColor crashPieceColor = crashPiece.getColor();
            TeamColor movingPieceColor  = movingPiece.getColor();

            return  crashPieceColor == movingPieceColor
                    || (crashPiece instanceof Po && movingPiece instanceof Po);
        }

        return crashPoints.size() > 0;
    }

    public void move(Point beforePoint, Point afterPoint) {
        Movable movingPiece = findByPoint(beforePoint);
        Movable updatedMoving = movingPiece.updatePoint(afterPoint);

        if(hasAttackedPiece(afterPoint)) {
            Movable target = findByPoint(afterPoint);
            this.attackedPieces.add(target);
            runningPieces.remove(target);
        }

        runningPieces.remove(movingPiece);
        runningPieces.add(updatedMoving);
    }


}
