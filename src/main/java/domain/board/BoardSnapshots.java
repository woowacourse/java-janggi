package domain.board;

import static java.util.stream.Collectors.counting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardSnapshots {
    private final List<BoardSnapshot> snapshots = new ArrayList<>();

    public void addSnapshot(BoardSnapshot boardSnapshot) {
        snapshots.add(boardSnapshot);
    }

    public boolean isSamePositionThreeTurnInGame() {
        Map<BoardSnapshot, Long> sameCount = snapshots.stream()
                .collect(Collectors.groupingBy(snapshot -> snapshot, counting()));

        return sameCount.values().stream()
                .anyMatch(count -> count >= 3);
    }
}
