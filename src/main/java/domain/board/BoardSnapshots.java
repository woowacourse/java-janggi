package domain.board;

import java.util.HashMap;
import java.util.Map;

public class BoardSnapshots {
    private final Map<BoardSnapshot, Long> snapshotCount = new HashMap<>();

    public boolean appearSamePositionThreeTurn(BoardSnapshot boardSnapshot) {
        snapshotCount.put(boardSnapshot, snapshotCount.getOrDefault(boardSnapshot, 0L) + 1);
        return snapshotCount.get(boardSnapshot) >= 3;
    }
}
