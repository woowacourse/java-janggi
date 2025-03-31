package janggi.board.dao;

import janggi.setting.CampType;
import java.util.HashMap;
import java.util.Map;

public class FakeTurnDao implements TurnDAO{
    private final Map<Integer, String> database = new HashMap<>();

    @Override
    public void insertQuery(CampType campType) {
        //inserQuery가 초나라 턴일때 처음 insert하니까 id column 1이 맞음
        database.put(1, campType.getName());
    }

    @Override
    public void updateQuery(CampType campType) {
        database.put(1, campType.getName());
    }

    @Override
    public CampType selectQuery() {
        return CampType.findCampType(database.get(1));
    }

    @Override
    public void dropTurnTable() {
        database.clear();
    }
}
