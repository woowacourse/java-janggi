package janggi.fixture;

import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import javax.swing.text.Position;

public class SetupPolicyTestFixture {

    public static Map<Position, PieceType> 상_마를_제외한_기물_배치_정보_제공() {
        return Stream.of(
            Map.entry(Position.valueOf(1, 1), PieceType.CHARIOT),
            Map.entry(Position.valueOf(1, 4), PieceType.GUARD),
            Map.entry(Position.valueOf(1, 6), PieceType.GUARD),
            Map.entry(Position.valueOf(1, 9), PieceType.CHARIOT),
            Map.entry(Position.valueOf(2, 5), PieceType.GENERAL),
            Map.entry(Position.valueOf(3, 2), PieceType.CANNON),
            Map.entry(Position.valueOf(3, 8), PieceType.CANNON),
            Map.entry(Position.valueOf(4, 1), PieceType.SOLDIER),
            Map.entry(Position.valueOf(4, 3), PieceType.SOLDIER),
            Map.entry(Position.valueOf(4, 5), PieceType.SOLDIER),
            Map.entry(Position.valueOf(4, 7), PieceType.SOLDIER),
            Map.entry(Position.valueOf(4, 9), PieceType.SOLDIER)
        );
    }
}
