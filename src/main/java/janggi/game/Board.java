package janggi.game;

import janggi.piece.InitialPieces;
import janggi.piece.Movable;
import janggi.piece.Po;
import janggi.point.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {

    private final List<Movable> runningPieces;
    private Team turn;

    public Board(List<Movable> runningPieces, Team startTeam) {
        this.runningPieces = runningPieces;
        this.turn = startTeam;
    }

    public static Board init(Team startTeam) {
        List<Movable> pieces = Arrays.stream(InitialPieces.values())
            .map(InitialPieces::getInitialPieces)
            .flatMap(List::stream)
            .toList();

        return new Board(pieces, startTeam);
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
        } else if (!movingPiece.isInMovingRange(afterPoint) || checkHurdles(beforePoint,
            movingPiece.findRoute(afterPoint))
        ) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }

        Movable updatedMoving = movingPiece.updatePoint(afterPoint);

        if (hasPieceOnPoint(afterPoint)) {
            Movable target = findByPoint(afterPoint);
            runningPieces.remove(target);
        }

        runningPieces.remove(movingPiece);
        runningPieces.add(updatedMoving);
    }

    public List<Movable> getRunningPieces() {
        return Collections.unmodifiableList(runningPieces);
    }

    public Team getTurn() {
        return turn;
    }
}
