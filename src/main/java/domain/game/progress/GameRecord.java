package domain.game.progress;

import java.util.ArrayList;
import java.util.List;

public class GameRecord {
    private final List<MoveLog> logs;

    public GameRecord() {
        this.logs = new ArrayList<>();
    }

    public GameRecord(List<MoveLog> initialLogs) {
        this.logs = new ArrayList<>(initialLogs);
    }

    public void append(MoveLog log) {
        logs.add(log);
    }

    public List<MoveLog> logs() {
        return List.copyOf(logs);
    }

    public int size() {
        return logs.size();
    }
}
