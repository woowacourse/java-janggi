package domain.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BoardSnapshots {
    private final List<BoardSnapshot> boardSnapshots = new ArrayList<>();

    public void addBoardSnapshot(BoardSnapshot boardSnapshot) {
        boardSnapshots.add(boardSnapshot);
    }

    public boolean appearSamePositionThreeTurn() {
        Map<BoardSnapshot, Long> snapshotCount = boardSnapshots.stream()
                .collect(Collectors.groupingBy(boardSnapshot -> boardSnapshot, Collectors.counting()));
        return snapshotCount.values().stream()
                .anyMatch(count -> count >= 3);
    }
}
