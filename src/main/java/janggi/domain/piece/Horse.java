package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import java.util.List;

public class Horse extends AbstractPiece {

    private static final PieceType PIECE_TYPE = PieceType.HORSE;

    public Horse(Team team) {
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
        return List.of(from.move(Direction.straightBetween(from, to)));
    }

    @Override
    public void canMove(PiecesOnPath piecesOnPath, Piece endPiece) {
        validateAllPieceEmpty(piecesOnPath);
        validateSameTeam(endPiece);
    }

    private void validateMove(Position from, Position to) {
        if (!from.matchesDistance(to, 1, 2)) {
            throw new IllegalArgumentException("[ERROR] 마는 해당 경로로 이동할 수 없습니다.");
        }
    }

    private void validateAllPieceEmpty(PiecesOnPath piecesOnPath) {
        if (!piecesOnPath.isAllEmpty()) {
            throw new IllegalArgumentException("[ERROR] 마의 이동 경로에 기물이 있을 수 없습니다.");
        }
    }
}
