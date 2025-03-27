package domain.dao;

import domain.position.JanggiPosition;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeMoveHistoryDao implements MoveHistoryDao {

    private class History {
        private final String game;
        private final String origin;
        private final String destination;

        public History(String game, String origin, String destination) {
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
    public void addHistory(String gameId, String originId, String destinationId) {
        histories.put(sequence, new History(gameId, originId, destinationId));
        sequence++;
    }

    @Override
    public void deleteAll() {
        histories.clear();
    }

    @Override
    public List<List<String>> getAllHistory(String gameId) {
        List<List<String>> allHistories = new ArrayList<>();
        for (History history : histories.values()) {
            allHistories.add(List.of(history.origin, history.destination));
        }
        return allHistories;
    }
}
