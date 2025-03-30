package piece;

import game.Board;
import position.Path;
import position.Position;

public class Rook extends Piece {

    public Rook(final Country country) {
        super(PieceType.ROOK, country);
    }

    @Override
    public Path findPathForMove(Position fromPosition, Position toPosition) {
        if (!fromPosition.isStraight(toPosition)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        return fromPosition.findStraightPath(toPosition);
    }

    @Override
    public void validatePath(final Path path, Board board) {
        path.validateNoObstacles(board);
    }
}
