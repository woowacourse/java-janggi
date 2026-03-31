package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Horse extends MoveablePiece {

    private static final String PIECE_NAME = "마";
    private static final int ONE_DIRECTION_MIN_DIFF = 1;
    private static final int ONE_DIRECTION_MAX_DIFF = 2;

    public Horse(Team team) {
        super(team);
    }

    @Override
    public boolean isSamePiece(Piece other) {
        return other instanceof Horse;
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

    private void validateMove(Position from, Position to) {
        int absRowDiff = Math.abs(from.calculateRowDiff(to));
        int absColumnDiff = Math.abs(from.calculateColumnDiff(to));

        boolean isValidMove = (absRowDiff == ONE_DIRECTION_MIN_DIFF && absColumnDiff == ONE_DIRECTION_MAX_DIFF)
                || (absRowDiff == ONE_DIRECTION_MAX_DIFF && absColumnDiff == ONE_DIRECTION_MIN_DIFF);

        if (!isValidMove) {
            throw new IllegalArgumentException("[ERROR] 마는 해당 경로로 이동할 수 없습니다.");
        }
    }

    private List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        path.add(from.nextStraight(to));
        return path;
    }

    private void validateAllPieceEmpty(List<Piece> piecesOnPath) {
        if (!piecesOnPath.stream().allMatch(Piece::isEmptyPiece)) {
            throw new IllegalArgumentException("[ERROR] 마의 이동 경로에 기물이 있을 수 없습니다.");
        }
    }
}
