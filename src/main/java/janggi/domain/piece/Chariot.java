package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Direction;
import janggi.domain.position.Palace;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Chariot extends AbstractPiece {

    private static final PieceType PIECE_TYPE = PieceType.CHARIOT;

    public Chariot(Team team) {
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
        validateAllPieceEmpty(piecesOnPath);
        validateSameTeam(endPiece);
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
            throw new IllegalArgumentException("[ERROR] 차는 직선으로만 이동할 수 있습니다.");
        }
    }

    private void validateAllPieceEmpty(PiecesOnPath piecesOnPath) {
        if (!piecesOnPath.isAllEmpty()) {
            throw new IllegalArgumentException("[ERROR] 차의 이동 경로에 기물이 있을 수 없습니다.");
        }
    }
}
