package domain.piece;

import domain.Board;
import domain.Country;
import domain.Direction;
import domain.Position;
import java.util.List;

public class Cannon extends Piece {
    private static final String ONLY_MOVE_STRAIGHT = "[ERROR] 포는 직선으로만 이동 가능합니다.";
    private static final String FIXED_DIRECTION = "[ERROR] 포는 하나의 방향으로만 이동 가능합니다.";
    private static final String CAN_NOT_MOVE_TO_POSITION = "[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.";

    private static final int CANNON_JUMP_PIECE_COUNT = 1;

    public Cannon(Country country) {
        super(new PieceInfo(PieceType.CANNON, country));
    }

    @Override
    public void validateDirections(List<Direction> directions) {
        Direction oneSide = directions.getFirst();
        boolean allSameDirection = directions.stream()
                .allMatch(direction -> direction.equals(oneSide));
        if (!allSameDirection) {
            throw new IllegalArgumentException(FIXED_DIRECTION);
        }
        // 궁성 영역 생각하지 않음
        if (oneSide.isDiagonal()) {
            throw new IllegalArgumentException(ONLY_MOVE_STRAIGHT);
        }
    }

    @Override
    public void validatePath(List<Position> paths, Board board) {
        int pieceCount = 0;
        for (int index = 0; index < paths.size() - 1; index++) {
            pieceCount = countPiece(paths.get(index), board, pieceCount);
        }
        if (pieceCount != CANNON_JUMP_PIECE_COUNT) {
            throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
        }
    }

    private int countPiece(Position position, Board board, int pieceCount) {
        if (board.isEmpty(position)) {
            return pieceCount;
        }
        if (board.isCannon(position)) {
            throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
        }
        return pieceCount + 1;
    }
}
