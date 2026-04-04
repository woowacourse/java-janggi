package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.domain.team.Team;

public class Chariot extends MoveablePiece {

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public PieceType getType() {
        return PieceType.CHARIOT;
    }

    @Override
    public Path getPath(Movement movement) {
        validateMove(movement);
        return findPath(movement.getFrom(), movement.getTo());
    }

    @Override
    public void validateCanMove(PieceOnPath piecesOnPath, Piece endPiece) {
        validateAllPieceEmpty(piecesOnPath);
        validateSameTeam(endPiece);
    }

    private void validateMove(Movement movement) {
        int rowDiff = movement.calculateRowDiff();
        int columnDiff = movement.calculateColumnDiff();

        if (rowDiff != 0 && columnDiff != 0) {
            throw new IllegalArgumentException("[ERROR] 차는 직선으로만 이동할 수 있습니다.");
        }
    }

    private Path findPath(Position from, Position to) {
        Path path = new Path();
        Position target = from.nextStraight(to);
        while (!target.equals(to)) {
            path.add(target);
            target = target.nextStraight(to);
        }
        return path;
    }

    private void validateAllPieceEmpty(PieceOnPath piecesOnPath) {
        if (!piecesOnPath.stream().allMatch(Piece::isEmptyPiece)) {
            throw new IllegalArgumentException("[ERROR] 차의 이동 경로에 기물이 있을 수 없습니다.");
        }
    }
}
