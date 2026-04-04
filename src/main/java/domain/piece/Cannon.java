package domain.piece;

import domain.Country;
import domain.Position;
import java.util.List;
import java.util.Map;

public class Cannon extends StraightMovingPiece {
    private static final String CANNON_CAN_NOT_CATCH_CANNON = "[ERROR] 포는 포를 잡을 수 없습니다.";

    private static final int REQUIRED_PIECES_ON_PATH = 2;

    public Cannon(Country country) {
        super(new PieceInfo(PieceType.CANNON, country));
    }

    @Override
    public void validateClearPath(Map<Position, PieceType> piecesOnPath, boolean isDestinationEmpty) {
        // 경로 중간에 포가 있는지 → pieceTypes의 값이 PieceType.CANNON인지 확인
        List<PieceType> pieceTypes = piecesOnPath.values().stream().toList();
        for (PieceType pieceType : pieceTypes) {
            if (pieceType == PieceType.CANNON) {
                throw new IllegalArgumentException(CANNON_CAN_NOT_CATCH_CANNON);
            }
        }

        // 경로 중간 기물이 정확히 1개인지 → pieceTypes.size()로 확인 가능
        if (!isDestinationEmpty && piecesOnPath.size() != REQUIRED_PIECES_ON_PATH) {
            throw new IllegalArgumentException("중간 기물은 1개여야 합니다");
        }

        // 목적지가 포인지 → pieceTypes의 마지막 값이 PieceType.CANNON인지 확인
        if (!isDestinationEmpty && pieceTypes.getLast() == PieceType.CANNON) {
            throw new IllegalArgumentException(CANNON_CAN_NOT_CATCH_CANNON);
        }
    }
}
