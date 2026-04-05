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
        List<PieceType> pieceTypes = piecesOnPath.values().stream().toList();
        for (PieceType pieceType : pieceTypes) {
            if (pieceType == PieceType.CANNON) {
                throw new IllegalArgumentException(CANNON_CAN_NOT_CATCH_CANNON);
            }
        }

        if (!isDestinationEmpty && piecesOnPath.size() != REQUIRED_PIECES_ON_PATH) {
            throw new IllegalArgumentException("중간 기물은 1개여야 합니다");
        }

        if (!isDestinationEmpty && pieceTypes.getLast() == PieceType.CANNON) {
            throw new IllegalArgumentException(CANNON_CAN_NOT_CATCH_CANNON);
        }
    }
}
