package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.domain.team.Team;

public class Chariot extends MoveablePiece {

    private final Palace palace;

    public Chariot(Team team, Palace palace) {
        super(team);
        this.palace = palace;
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }

    @Override
    public Path getPath(Movement movement) {
        validateMove(movement);
        return findPath(movement);
    }

    @Override
    public void validateCanMove(PieceOnPath pieceOnPath, Piece endPiece) {
        validateAllPieceEmpty(pieceOnPath);
        validateSameTeam(endPiece);
    }

    private void validateMove(Movement movement) {
        if (!(isStraightMove(movement) || isPalaceMove(movement))) {
            throw new IllegalArgumentException("[ERROR] 차는 한 방향으로만 이동할 수 있습니다.");
        }
    }

    private boolean isStraightMove(Movement movement) {
        int rowDiff = movement.calculateRowDiff();
        int columnDiff = movement.calculateColumnDiff();

        return rowDiff == 0 || columnDiff == 0;
    }

    private boolean isPalaceMove(Movement movement) {
        return palace.hasRoute(movement.getFrom(), movement.getTo());
    }

    private Path findPath(Movement movement) {
        if (isDiagonalMove(movement)) {
            return findDiagonalPath(movement);
        }
        return findStraightPath(movement);
    }

    private boolean isDiagonalMove(Movement movement) {
        int absRowDiff = Math.abs(movement.calculateRowDiff());
        int absColumnDiff = Math.abs(movement.calculateColumnDiff());
        return absRowDiff == absColumnDiff;
    }

    private Path findDiagonalPath(Movement movement) {
        Path path = new Path();
        Position to = movement.getTo();
        Position target = movement.getFrom().nextDiagonal(to);
        while (!target.equals(to)) {
            path.add(target);
            target = target.nextDiagonal(to);
        }
        return path;
    }

    private Path findStraightPath(Movement movement) {
        Path path = new Path();
        Position to = movement.getTo();
        Position target = movement.getFrom().nextStraight(to);
        while (!target.equals(to)) {
            path.add(target);
            target = target.nextStraight(to);
        }
        return path;
    }

    private void validateAllPieceEmpty(PieceOnPath piecesOnPath) {
        if (piecesOnPath.countNonEmpty() != 0) {
            throw new IllegalArgumentException("[ERROR] 차의 이동 경로에 기물이 있을 수 없습니다.");
        }
    }
}
