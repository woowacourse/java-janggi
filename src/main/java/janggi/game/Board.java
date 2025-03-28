package janggi.game;

import janggi.piece.Byeong;
import janggi.piece.Cha;
import janggi.piece.Gung;
import janggi.piece.Ma;
import janggi.piece.Piece;
import janggi.piece.Po;
import janggi.piece.Sa;
import janggi.piece.Sang;
import janggi.movement.middleRoute.Hurdles;
import janggi.point.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
    private final List<Piece> runningPieces;
    private final List<Piece> attackedPieces;
    private Team turn;

    public Board(List<Piece> runningPieces) {
        this.runningPieces = runningPieces;
        this.attackedPieces = new ArrayList<>();
        this.turn = Team.CHO;
    }

    public static Board init() {
        List<Piece> pieces = new ArrayList<>();
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

    public Piece findByPoint(Point point) {
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
        Piece movingPiece = findByPoint(beforePoint);
        validatePieceTeam(movingPiece);
        validatePieceMovable(afterPoint, movingPiece);

        Piece updatedMoving = movingPiece.updatePoint(afterPoint);
        removeAttackedPiece(afterPoint);
        updateMovedPiece(movingPiece, updatedMoving);
    }

    private void validatePieceTeam(Piece movingPiece) {
        if (turn != movingPiece.getTeam()) {
            throw new IllegalArgumentException(turn.getText() + "의 기물만 이동할 수 있습니다.");
        }
    }

    private void validatePieceMovable(Point afterPoint, Piece movingPiece) {
        if (!movingPiece.canMove(afterPoint, findHurdles())) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
    }

    private void removeAttackedPiece(Point afterPoint) {
        if (hasPieceOnPoint(afterPoint)) {
            Piece prey = findByPoint(afterPoint);
            runningPieces.remove(prey);
            attackedPieces.add(prey);
        }
    }

    private void updateMovedPiece(Piece movingPiece, Piece updatedMoving) {
        runningPieces.remove(movingPiece);
        runningPieces.add(updatedMoving);
    }

    public Hurdles findHurdles() {
        Map<Point, Piece> hurdles = new HashMap<>();
        runningPieces.forEach(piece ->
                hurdles.put(piece.getPoint(), piece)
        );
        return new Hurdles(hurdles);
    }

    public List<Piece> getRunningPieces() {
        return Collections.unmodifiableList(runningPieces);
    }

    public Team getTurn() {
        return turn;
    }
}
