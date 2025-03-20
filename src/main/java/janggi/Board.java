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
    private Team turn;

    public Board(List<Movable> runningPieces) {
        this.runningPieces = runningPieces;
        this.attackedPieces = new ArrayList<>();
        this.turn = Team.CHO;
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

    public Team getTurn() {
        return turn;
    }

    public void reverseTurn() {
        this.turn = turn.reverse();
    }

    public Movable findByPoint(Point point) {
        return runningPieces.stream()
                .filter(piece -> piece.getPoint().equals(point))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 좌표에 기물이 존재하지 않습니다."));
    }

    public boolean hasPieceOnPoint(Point point) {
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
            Team movingPieceColor = movingPiece.getTeam();

            return crashPieceColor == movingPieceColor;
        }

        return crashPoints.size() > 0;
    }

    public void move(Point beforePoint, Point afterPoint) {
        Movable movingPiece = findByPoint(beforePoint);

        if (turn != movingPiece.getTeam()) {
            throw new IllegalArgumentException(turn.getText() + "의 기물만 이동할 수 있습니다.");
        }

        if (movingPiece instanceof Po) {
            if (!((Po) movingPiece).isMovable(afterPoint, this)) {
                throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
            }
        } else if (!movingPiece.isInMovingRange(afterPoint) || checkHurdles(beforePoint, movingPiece.findRoute(afterPoint))
        ) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }

        Movable updatedMoving = movingPiece.updatePoint(afterPoint);

        if (hasPieceOnPoint(afterPoint)) {
            Movable target = findByPoint(afterPoint);
            this.attackedPieces.add(target);
            runningPieces.remove(target);
        }

        runningPieces.remove(movingPiece);
        runningPieces.add(updatedMoving);
    }
}
