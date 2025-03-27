package domain.dao;

import domain.position.JanggiPosition;
import java.util.HashMap;
import java.util.Map;

public class FakeJanggiPositionDao implements JanggiPositionDao {

    private final Map<Integer, JanggiPosition> positions;
    private int sequence;

    public FakeJanggiPositionDao() {
        this.positions = new HashMap<>();
        this.sequence = 1;
    }

    @Override
    public void addPosition(JanggiPosition position) {
        positions.put(sequence, position);
        sequence++;
    }

    @Override
    public int findByPosition(JanggiPosition position) {
        if (!positions.containsValue(position)) {
            addPosition(position);
        }

        return positions.keySet().stream()
                .filter(id -> positions.get(id).equals(position))
                .findFirst()
                .orElseThrow();
    }

    @Override
    public JanggiPosition findPositionById(int positionId) {
        return positions.get(positionId);
    }

    @Override
    public void deleteAll() {
        positions.clear();
    }
}
