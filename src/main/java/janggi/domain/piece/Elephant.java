package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Elephant implements Piece {

    private static final String PIECE_NAME = "상";

    private final Team team;

    public Elephant(Team team) {
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
        validateAllPieceEmpty(piecesOnPath);
        validateSameTeam(endPiece);
        return true;
    }

    private List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        Position next = from.moveStraight(to);
        path.add(next);
        path.add(next.moveDiagonal(to));
        return path;
    }

    private void validateMove(Position from, Position to) {
        if (!from.hasOffsetPairs(to, 2, 3)) {
            throw new IllegalArgumentException("[ERROR] 상은 해당 경로로 이동할 수 없습니다.");
        }
    }

    private void validateAllPieceEmpty(List<Piece> piecesOnPath) {
        if (!piecesOnPath.stream().allMatch(Piece::isEmptyPiece)) {
            throw new IllegalArgumentException("[ERROR] 상의 이동 경로에 기물이 있을 수 없습니다.");
        }
    }

    private void validateSameTeam(Piece endPiece) {
        if (isSameTeam(endPiece)) {
            throw new IllegalArgumentException("[ERROR] 자신의 기물로 이동할 수 없습니다.");
        }
    }
}
