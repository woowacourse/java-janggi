package janggi.fixture;

import janggi.domain.piece.PieceType;
import janggi.domain.Position;
import java.util.LinkedHashMap;
import java.util.Map;

public class SetupPolicyTestFixture {

    public static Map<Position, PieceType> 상_마를_제외한_기물_배치_정보_제공() {
        Map<Position, PieceType> map = new LinkedHashMap<>();
        map.put(Position.valueOf(1, 1), PieceType.CHARIOT);
        map.put(Position.valueOf(1, 4), PieceType.GUARD);
        map.put(Position.valueOf(1, 6), PieceType.GUARD);
        map.put(Position.valueOf(1, 9), PieceType.CHARIOT);
        map.put(Position.valueOf(2, 5), PieceType.GENERAL);
        map.put(Position.valueOf(3, 2), PieceType.CANNON);
        map.put(Position.valueOf(3, 8), PieceType.CANNON);
        map.put(Position.valueOf(4, 1), PieceType.SOLDIER);
        map.put(Position.valueOf(4, 3), PieceType.SOLDIER);
        map.put(Position.valueOf(4, 5), PieceType.SOLDIER);
        map.put(Position.valueOf(4, 7), PieceType.SOLDIER);
        map.put(Position.valueOf(4, 9), PieceType.SOLDIER);
        return map;
    }
}
