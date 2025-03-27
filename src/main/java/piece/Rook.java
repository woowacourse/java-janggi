package piece;

import game.Board;
import java.util.List;
import position.Position;

public class Rook extends Piece {

    public Rook(final Country country) {
        super(PieceType.ROOK, country);
    }

    public List<Position> getPathForMoving(Position fromPosition, Position toPosition) {
        if (!fromPosition.isStraight(toPosition)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        return fromPosition.findStraightPositions(toPosition);
    }

    @Override
    public void validateRoute(final List<Position> positions, Board board) {
        if (positions.subList(0, positions.size() - 1).stream()
                .anyMatch(position -> board.getBoard().containsKey(position))) {
            throw new IllegalArgumentException("중간에 기물이 있어 갈 수 없습니다.");
        }
    }


}
