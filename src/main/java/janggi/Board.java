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
            Team crashPieceColor = crashPiece.getTeam();
            Team movingPieceColor  = movingPiece.getTeam();

            return  crashPieceColor == movingPieceColor;
        }

        return crashPoints.size() > 0;
    }

    public void move(Point beforePoint, Point afterPoint) {
        Movable movingPiece = findByPoint(beforePoint);


        if (movingPiece instanceof Po) {
            if (!isPoMovable((Po) movingPiece, afterPoint) || checkPoHurdles(beforePoint, movingPiece.findRoute(afterPoint))) {
                throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
            }
        } else if (!movingPiece.isMovable(afterPoint) || checkHurdles(beforePoint, movingPiece.findRoute(afterPoint))
        ) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }

        Movable updatedMoving = movingPiece.updatePoint(afterPoint);

        if(hasAttackedPiece(afterPoint)) {
            Movable target = findByPoint(afterPoint);
            this.attackedPieces.add(target);
            runningPieces.remove(target);
        }

        runningPieces.remove(movingPiece);
        runningPieces.add(updatedMoving);
    }

    public boolean isPoMovable(Po po, Point targetPoint) {
        Point point = po.getPoint();
        if (point.isSameRow(targetPoint)) {
            for (Movable movable : findPieceOnHorizontalRoute(point)) {
                if (movable.getPoint().isColumnBetween(point, targetPoint) && !(movable instanceof Po)) {
                    return true;
                }
            }
        }
        if (point.isSameColumn(targetPoint)) {
            for (Movable movable : findPieceOnVerticalRoute(point)) {
                if (movable.getPoint().isRowBetween(point, targetPoint) && !(movable instanceof Po)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkPoHurdles(Point startPoint, List<Point> route) {
        //포인데 널뛰기를 하고 나서도 장애물이 있느냐?
        List<Point> piecePoints = runningPieces.stream()
                .map(Movable::getPoint).toList();

        List<Point> crashPoints = new ArrayList<>(route.stream()
                .filter(piecePoints::contains)
                .toList());
        crashPoints.removeFirst();

        if (crashPoints.size() == 1
                && route.getLast().equals(crashPoints.getFirst())
        ) {
            Movable crashPiece = findByPoint(crashPoints.getFirst());
            Movable movingPiece = findByPoint(startPoint);

            return crashPiece instanceof Po && movingPiece instanceof Po;
        }

        return crashPoints.size() > 0;
    }
}
