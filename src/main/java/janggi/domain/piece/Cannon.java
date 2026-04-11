package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Direction;
import janggi.domain.position.Palace;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends AbstractPiece {

    private static final PieceType PIECE_TYPE = PieceType.CANNON;

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public PieceType getType() {
        return PIECE_TYPE;
    }

    @Override
    public boolean isSameType(PieceType type) {
        return PIECE_TYPE == type;
    }

    @Override
    public List<Position> getPath(Position from, Position to) {
        if (Palace.isDiagonalMove(from, to)) {
            return Palace.getDiagonalPath(from, to);
        }
        validateStraightMove(from, to);
        return findPath(from, to);
    }

    @Override
    public void canMove(PiecesOnPath piecesOnPath, Piece endPiece) {
        validateJumpOnlyOnePiece(piecesOnPath);
        validateJumpCannon(piecesOnPath);
        validateSameTeam(endPiece);
        validateEndCannon(endPiece);
    }

    private List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        Direction direction = Direction.straightBetween(from, to);
        Position target = from.move(direction);
        while (target.hasOnlyStraightMove(to)) {
            path.add(target);
            target = target.move(direction);
        }
        return path;
    }

    private void validateStraightMove(Position from, Position to) {
        if (!from.hasOnlyStraightMove(to)) {
            throw new IllegalArgumentException("[ERROR] 포는 직선으로만 이동할 수 있습니다.");
        }
    }

    private void validateEndCannon(Piece endPiece) {
        if (isSamePiece(endPiece)) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 잡을 수 없습니다.");
        }
    }

    private void validateJumpCannon(PiecesOnPath piecesOnPath) {
        if (piecesOnPath.containsType(PieceType.CANNON)) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 뛰어넘을 수 없습니다.");
        }
    }

    private void validateJumpOnlyOnePiece(PiecesOnPath piecesOnPath) {
        if (!piecesOnPath.hasExactlyOneNonEmpty()) {
            throw new IllegalArgumentException("[ERROR] 포는 오직 1개의 기물만 뛰어넘고 이동할 수 있습니다.");
        }
    }
}
