package domain.piece.policy;

import domain.piece.PieceType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PolicyProvider {
    private static final Map<PieceType, List<MovementPolicy>> provideMap;

    static {
        provideMap = new HashMap<>();

        provideMap.put(PieceType.CHA, List.of(new NormalMovementPolicy(), new GungseongDiagonalMovementPolicy()));
        provideMap.put(PieceType.MA, List.of(new NormalMovementPolicy()));
        provideMap.put(PieceType.SANG, List.of(new NormalMovementPolicy()));
        provideMap.put(PieceType.SA,
                List.of(new NormalMovementPolicy(), new GungseongBoundaryMovementPolicy(),
                        new GungseongDiagonalMovementPolicy()));
        provideMap.put(PieceType.JANG, List.of(
                new NormalMovementPolicy(),
                new GungseongBoundaryMovementPolicy(),
                new GungseongDiagonalMovementPolicy()));
        provideMap.put(PieceType.PO, List.of(new PoMovementPolicy(), new GungseongDiagonalMovementPolicy()));
        provideMap.put(PieceType.JOL, List.of(new NormalMovementPolicy(), new GungseongDiagonalMovementPolicy()));
        provideMap.put(PieceType.BYEONG, List.of(new NormalMovementPolicy(), new GungseongDiagonalMovementPolicy()));
    }

    private PolicyProvider() {
    }

    public static List<MovementPolicy> getPolicies(PieceType pieceType) {
        return provideMap.get(pieceType);
    }
}
