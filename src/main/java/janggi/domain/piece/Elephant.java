package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Elephant extends AbstractPiece {

    private static final PieceType PIECE_TYPE = PieceType.ELEPHANT;

    public Elephant(Team team) {
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
        validateMove(from, to);
        return findPath(from, to);
    }

    @Override
    public void canMove(PiecesOnPath piecesOnPath, Piece endPiece) {
        validateAllPieceEmpty(piecesOnPath);
        validateSameTeam(endPiece);
    }

    private List<Position> findPath(Position from, Position to) {
        List<Position> path = new ArrayList<>();
        Direction straight = Direction.straightBetween(from, to);
        Position next = from.move(straight);
        path.add(next);
        path.add(next.move(Direction.diagonalBetween(next, to)));
        return path;
    }

    private void validateMove(Position from, Position to) {
        if (!from.matchesDistance(to, 2, 3)) {
            throw new IllegalArgumentException("[ERROR] 상은 해당 경로로 이동할 수 없습니다.");
        }
    }

    private void validateAllPieceEmpty(PiecesOnPath piecesOnPath) {
        if (!piecesOnPath.isAllEmpty()) {
            throw new IllegalArgumentException("[ERROR] 상의 이동 경로에 기물이 있을 수 없습니다.");
        }
    }
}
