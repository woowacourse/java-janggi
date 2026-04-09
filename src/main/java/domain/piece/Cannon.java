package domain.piece;

import domain.board.Country;
import java.util.List;

public class Cannon extends StraightMovingPiece {
    private static final String CAN_NOT_MOVE_TO_POSITION = "[ERROR] 해당 경로로 기물을 이동시킬 수 없습니다.";
    private static final String CANNON_CAN_NOT_CATCH_CANNON = "[ERROR] 포는 포를 잡을 수 없습니다.";

    private static final int REQUIRED_PIECES_ON_PATH = 1;

    public Cannon(Country country) {
        super(new PieceInfo(PieceType.CANNON, country));
    }

    @Override
    public void validateClearPath(List<PieceType> pieceTypes, PieceType destinationPieceType) {
        if (pieceTypes.size() != REQUIRED_PIECES_ON_PATH) {
            throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
        }

        for (PieceType pieceType : pieceTypes) {
            if (pieceType == PieceType.CANNON) {
                throw new IllegalArgumentException(CAN_NOT_MOVE_TO_POSITION);
            }
        }

        if (destinationPieceType == PieceType.CANNON) {
            throw new IllegalArgumentException(CANNON_CAN_NOT_CATCH_CANNON);
        }
    }
}
