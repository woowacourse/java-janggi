package piece;

import game.Board;
import java.util.List;
import position.Position;

public class Cannon extends Piece {
    public Cannon(final Country country) {
        super(PieceType.CANNON, country);
    }

    public List<Position> getPathForMoving(Position fromPosition, Position toPosition) {
        if (!fromPosition.isStraight(toPosition)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        return fromPosition.findStraightPositions(toPosition);
    }

    @Override
    public void validateRoute(final List<Position> positions, Board board) {
        List<Piece> list = positions.subList(0, positions.size() - 1).stream()
                .filter(position -> board.getBoard().containsKey(position))
                .map(position -> board.getBoard().get(position))
                .toList();
        if (list.size() != 1 || list.getFirst().getPieceType() == PieceType.CANNON) {
            throw new IllegalArgumentException("포는 해당 위치로 이동할 수 없습니다.");
        }

    }
}
