package domain.policy;

import domain.piece.PieceType;

import java.util.List;
import java.util.Map;

public class MovePolicyRegistry {

    private final Map<PieceType, List<MovePolicy>> moveRules;

    public MovePolicyRegistry() {
        this.moveRules = Map.of(
                PieceType.PAWN, List.of(
                        new BasicCapturePolicy()
                ),
                PieceType.HORSE, List.of(
                        new BasicCapturePolicy(),
                        new MiddlePathBlockPolicy()
                ),
                PieceType.ELEPHANT, List.of(
                        new BasicCapturePolicy(),
                        new MiddlePathBlockPolicy()
                ),
                PieceType.CHARIOT, List.of(
                        new BasicCapturePolicy(),
                        new PathBlockedPolicy()
                ),
                PieceType.CANNON, List.of(
                        new BasicCapturePolicy(),
                        new CannonCapturePolicy(),
                        new CannonJumpPolicy()
                ),
                PieceType.GUARD, List.of(
                        new BasicCapturePolicy(),
                        new PalaceBoundaryPolicy()
                ),
                PieceType.KING, List.of(
                        new BasicCapturePolicy(),
                        new PalaceBoundaryPolicy()
                )
        );
    }

    public List<MovePolicy> findBy(PieceType pieceType) {
        return moveRules.get(pieceType);
    }
}
