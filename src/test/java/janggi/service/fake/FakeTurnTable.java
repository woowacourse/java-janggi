package janggi.service.fake;

import janggi.domain.board.dao.TurnDAO;
import janggi.domain.setting.CampType;
import java.util.HashMap;
import java.util.Map;

public class FakeTurnTable implements TurnDAO {
    private final Map<Integer, String> database = new HashMap<>();

    private static final int TURN_ID = 1;

    @Override
    public void insertQuery(CampType campType) {
        database.put(TURN_ID, CampType.CHO.getName());
    }

    @Override
    public void updateQuery(CampType campType) {
        database.put(TURN_ID, campType.getName());
    }

    @Override
    public CampType selectQuery() {
        return CampType.findCampType(database.get(TURN_ID));
    }

    @Override
    public void dropTurnTable() {
        database.clear();
    }
}
