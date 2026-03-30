package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Cannon implements Piece {

    private static final String PIECE_NAME = "포";

    private final Team team;

    public Cannon(Team team) {
        this.team = team;
    }

    @Override
    public boolean isEmptyPiece() {
        return false;
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return other.getDisplayName().equals(PIECE_NAME);
    }

    @Override
    public boolean isSameTeam(Piece other) {
        return other.isSame(team);
    }

    @Override
    public boolean isSame(Team team) {
        return this.team == team;
    }

    @Override
    public String getDisplayName() {
        return PIECE_NAME;
    }

    @Override
    public List<Position> getPath(Position from, Position to) {
        validateMove(from, to);
        return findPath(from, to);
    }

    @Override
    public boolean canMove(List<Piece> piecesOnPath, Piece endPiece) {
        validateJumpOnlyOnePiece(piecesOnPath);
        validateJumpCannon(piecesOnPath);
        validateSameTeam(endPiece);
        validateEndCannon(endPiece);
        return true;
    }

    private List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        Position target = from.moveStraight(to);
        while (target.hasOnlyStraightMove(to)) {
            path.add(target);
            target = target.moveStraight(to);
        }
        return path;
    }

    private void validateMove(Position from, Position to) {
        if (!from.hasOnlyStraightMove(to)) {
            throw new IllegalArgumentException("[ERROR] 포는 직선으로만 이동할 수 있습니다.");
        }
    }

    private void validateEndCannon(Piece endPiece) {
        if (isSamePiece(endPiece)) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
        }
    }

    private void validateSameTeam(Piece endPiece) {
        if (isSameTeam(endPiece)) {
            throw new IllegalArgumentException("[ERROR] 자신의 기물로 이동할 수 없습니다.");
        }
    }

    private void validateJumpCannon(List<Piece> piecesOnPath) {
        if (piecesOnPath.stream()
                .anyMatch(this::isSamePiece)) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
        }
    }

    private void validateJumpOnlyOnePiece(List<Piece> piecesOnPath) {
        if (piecesOnPath.stream()
                .filter(piece -> !piece.isEmptyPiece()).count() != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 오직 1개의 기물을 뛰어넘고 이동할 수 있습니다.");
        }
    }
}
