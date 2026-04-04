package janggi.domain.piece;

import janggi.domain.path.Path;
import janggi.domain.path.PieceOnPath;
import janggi.domain.position.Movement;
import janggi.domain.position.Position;
import janggi.domain.team.Team;

public class Cannon extends MoveablePiece {

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public PieceType getType() {
        return PieceType.CANNON;
    }

    @Override
    public Path getPath(Movement movement) {
        validateMove(movement);
        return findPath(movement.getFrom(), movement.getTo());
    }

    @Override
    public void validateCanMove(PieceOnPath piecesOnPath, Piece endPiece) {
        validateJumpOnlyOnePiece(piecesOnPath);
        validateJumpCannon(piecesOnPath);
        validateSameTeam(endPiece);
        validateEndCannon(endPiece);
    }

    private void validateMove(Movement movement) {
        int rowDiff = movement.calculateRowDiff();
        int columnDiff = movement.calculateColumnDiff();

        if (rowDiff != 0 && columnDiff != 0) {
            throw new IllegalArgumentException("[ERROR] 포는 직선으로만 이동할 수 있습니다.");
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

    private void validateJumpOnlyOnePiece(PieceOnPath piecesOnPath) {
        if (piecesOnPath.stream()
                .filter(piece -> !piece.isEmptyPiece()).count() != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 오직 1개의 기물을 뛰어넘고 이동할 수 있습니다.");
        }
    }

    private void validateEndCannon(Piece endPiece) {
        if (endPiece.getType() == PieceType.CANNON) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
        }
    }

    private void validateJumpCannon(PieceOnPath piecesOnPath) {
        if (piecesOnPath.stream()
                .anyMatch(piece -> piece.getType() == PieceType.CANNON)) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
        }
    }
}
