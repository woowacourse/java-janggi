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

    // 애초에 루트를 따로 중간 기물 계산이랑 최종 위치 계산이랑 따로 하자
    @Override
    public void validateRoute(final List<Position> positions, Board board) {
        boolean existCannon = positions.stream()
                .filter(position -> board.getBoard().containsKey(position))
                .anyMatch(position -> board.getBoard().get(position).getPieceType() == PieceType.CANNON);
        if (existCannon) {
            throw new IllegalArgumentException("포는 포를 먹거나 넘을 수 없습니다. ");
        }

        List<Piece> list = positions.stream()
                .filter(position -> board.getBoard().containsKey(position))
                .map(position -> board.getBoard().get(position))
                .toList();
        if (list.size() != 1) {
            throw new IllegalArgumentException("포는 해당 위치로 이동할 수 없습니다.");
        }

    }

    @Override
    public void validateSpecialPieceTargetPosition(Position toPosition, Board board) {
        if (board.getBoard().containsKey(toPosition)) {
            Piece toPiece = board.getBoard().get(toPosition);
            if (toPiece.getPieceType() == PieceType.CANNON) {
                throw new IllegalArgumentException("포는 포를 먹을 수 없습니다.");
            }
        }
    }
}
