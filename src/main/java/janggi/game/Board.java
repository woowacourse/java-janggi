package janggi.game;

import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Ma;
import janggi.piece.Movable;
import janggi.piece.Po;
import janggi.piece.Sa;
import janggi.piece.Sang;
import janggi.movement.route.Hurdles;
import janggi.point.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final List<Movable> runningPieces;
    private Team turn;

    public Board(List<Movable> runningPieces) {
        this.runningPieces = runningPieces;
        this.turn = Team.CHO;
    }

    public static Board init() {
        List<Movable> pieces = new ArrayList<>();
        for (Team team : Team.values()) {
            pieces.addAll(Gung.init(team));
            pieces.addAll(Sa.init(team));
            pieces.addAll(Ma.init(team));
            pieces.addAll(Sang.init(team));
            pieces.addAll(Cha.init(team));
            pieces.addAll(Po.init(team));
            pieces.addAll(Byeong.init(team));
        }
        return new Board(pieces);
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

    public void move(Point beforePoint, Point afterPoint) {
        Movable movingPiece = findByPoint(beforePoint);
        validatePieceTeam(movingPiece);
        validatePieceMovable(afterPoint, movingPiece);

        Movable updatedMoving = movingPiece.updatePoint(afterPoint);
        removeAttackedPiece(afterPoint);
        updateMovedPiece(movingPiece, updatedMoving);
    }

    private void validatePieceTeam(Movable movingPiece) {
        if (turn != movingPiece.getTeam()) {
            throw new IllegalArgumentException(turn.getText() + "의 기물만 이동할 수 있습니다.");
        }
    }

    private void validatePieceMovable(Point afterPoint, Movable movingPiece) {
        if (!movingPiece.canMove(afterPoint, findHurdles())) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
    }

    private void removeAttackedPiece(Point afterPoint) {
        if (hasPieceOnPoint(afterPoint)) {
            Movable prey = findByPoint(afterPoint);
            runningPieces.remove(prey);
        }
    }

    private void updateMovedPiece(Movable movingPiece, Movable updatedMoving) {
        runningPieces.remove(movingPiece);
        runningPieces.add(updatedMoving);
    }

    public Hurdles findHurdles() {
        Map<Point, Movable> hurdles = new HashMap<>();
        runningPieces.forEach(piece ->
                hurdles.put(piece.getPoint(), piece)
        );
        return new Hurdles(hurdles);
    }

    public List<Movable> getRunningPieces() {
        return Collections.unmodifiableList(runningPieces);
    }

    public Team getTurn() {
        return turn;
    }
}
