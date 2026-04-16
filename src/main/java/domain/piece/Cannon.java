package domain.piece;

import domain.path.Paths;
import domain.strategy.PieceMoveStrategy;
import domain.vo.Position;
import java.util.List;

public class Cannon extends Piece {

    private static final String CANNOT_JUMP_WITH_CANNON = "포를 넘어갈 수 없습니다.";
    private static final String CANNOT_CAPTURE_CANNON_WITH_CANNON = "포는 포끼리 잡을 수 없습니다.";

    private static final String NAME = "포";

    public Cannon(Side side, Paths paths, PieceMoveStrategy strategy) {
        super(side, paths, strategy);
    }

    @Override
    public List<Position> findRoute(Position sourcePosition, Position targetPosition) {
        return strategy.findRoute(paths.getPaths(sourcePosition), sourcePosition, targetPosition);
    }

    @Override
    public void checkRoute(List<Piece> pieces) {
        int count = 0;
        for (Piece piece : pieces) {
            if (piece instanceof Cannon) {
                throw new IllegalArgumentException(CANNOT_JUMP_WITH_CANNON);
            }
            if (!(piece instanceof Empty)) {
                count++;
            }
        }
        if (count != 1) {
            throw new IllegalArgumentException(INVALID_TARGET_POSITION);
        }
    }

    @Override
    public void checkTarget(Piece piece) {
        if (piece.isSameSide(side)) {
            throw new IllegalArgumentException(CANNOT_CAPTURE_OWN_PIECE);
        }

        if (piece instanceof Cannon) {
            throw new IllegalArgumentException(CANNOT_CAPTURE_CANNON_WITH_CANNON);
        }
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getScore() {
        return PieceType.CANNON.score;
    }
}
