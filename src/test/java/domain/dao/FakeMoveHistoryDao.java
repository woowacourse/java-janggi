package domain.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeMoveHistoryDao implements MoveHistoryDao {

    private class History {
        private final int game;
        private final int origin;
        private final int destination;

        public History(int game, int origin, int destination) {
            this.game = game;
            this.origin = origin;
            this.destination = destination;
        }
    }

    private final Map<Integer, History> histories;
    private int sequence;

    public FakeMoveHistoryDao() {
        this.histories = new HashMap<>();
        this.sequence = 1;
    }

    @Override
    public void addHistory(int gameId, int originId, int destinationId) {
        histories.put(sequence, new History(gameId, originId, destinationId));
        sequence++;
    }

    @Override
    public void deleteAll() {
        histories.clear();
    }

    @Override
    public List<List<Integer>> getAllHistory(int gameId) {
        List<List<Integer>> allHistories = new ArrayList<>();
        for (History history : histories.values()) {
            allHistories.add(List.of(history.origin, history.destination));
        }
        return allHistories;
    }
}
