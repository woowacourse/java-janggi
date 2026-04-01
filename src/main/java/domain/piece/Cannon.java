package domain.piece;

import domain.Board;
import domain.Country;
import domain.Position;
import java.util.List;

public class Cannon extends StraightMovingPiece {
    private static final String CAN_NOT_MOVE_TO_POSITION = "[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.";
    private static final String CANNON_CAN_NOT_CATCH_CANNON = "[ERROR] 포는 포를 잡을 수 없습니다.";

    private static final int CANNON_JUMP_PIECE_COUNT = 1;

    public Cannon(Country country) {
        super(new PieceInfo(PieceType.CANNON, country));
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

        // TODO: 포는 포를 잡을 수 없음, validateDirections에서 해당 로직을 수행하면 차도 해당해버림, validatePath에서 수행하는 것이 옳음
        if (board.isCannon(paths.getLast())) {
            throw new IllegalArgumentException(CANNON_CAN_NOT_CATCH_CANNON);
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
