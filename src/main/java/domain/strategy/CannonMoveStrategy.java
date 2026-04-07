package domain.strategy;

import domain.piece.PieceInfo;
import domain.piece.PieceInfos;
import domain.piece.PieceType;

public class CannonMoveStrategy extends StraightMoveStrategy {
    private static final String INVALID_JUMP_PIECE_COUNT = "[ERROR] 포는 하나의 기물만 뛰어 넘을 수 있습니다.";
    private static final String CANNOT_JUMP_CANNON = "[ERROR] 포는 포를 뛰어 넘을 수 없습니다.";
    private static final String CANNOT_KILL_CANNON = "[ERROR] 포는 포를 잡을 수 없습니다.";

    private static final int CANNON_JUMP_PIECE_COUNT = 1;

    @Override
    public void validateToPositionWithFromPosition(PieceInfo fromPiece, PieceInfo toPiece) {
        super.validateToPositionWithFromPosition(fromPiece, toPiece);
        if (toPiece.pieceType() == PieceType.CANNON) {
            throw new IllegalArgumentException(CANNOT_KILL_CANNON);
        }
    }

    @Override
    public void validatePath(PieceInfos pieceInfos) {
        if (pieceInfos.getSize() != CANNON_JUMP_PIECE_COUNT) {
            throw new IllegalArgumentException(INVALID_JUMP_PIECE_COUNT);
        }
        PieceInfo pieceInfo = pieceInfos.getValues().getFirst();
        if (pieceInfo.pieceType() == PieceType.CANNON) {
            throw new IllegalArgumentException(CANNOT_JUMP_CANNON);
        }
    }
}
