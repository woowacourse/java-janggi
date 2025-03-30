package janggi.game;

import janggi.dto.MovementDto;
import janggi.movement.target.AttackedPiece;
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

    public Board(List<Piece> runningPieces) {
        this.runningPieces = runningPieces;
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

    public MovementDto move(Piece movingPiece, Point afterPoint) {
        validatePieceMovable(afterPoint, movingPiece);
        Piece updatedMovingPiece = movingPiece.updatePoint(afterPoint);
        AttackedPiece attackedPiece = removePieceIfAttacked(afterPoint);
        updateMovedPiece(movingPiece, updatedMovingPiece);
        return new MovementDto(updatedMovingPiece, attackedPiece);
    }

    private void validatePieceMovable(Point afterPoint, Piece movingPiece) {
        if (!movingPiece.canMove(afterPoint, findHurdles())) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
    }

    private AttackedPiece removePieceIfAttacked(Point afterPoint) {
        if (hasPieceOnPoint(afterPoint)) {
            Piece attackedPiece = findByPoint(afterPoint);
            runningPieces.remove(attackedPiece);
            return new AttackedPiece(attackedPiece);
        }
        return AttackedPiece.notAttacked();
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
}
