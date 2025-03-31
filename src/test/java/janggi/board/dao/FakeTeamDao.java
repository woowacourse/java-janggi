package janggi.board.dao;

import janggi.setting.CampType;
import java.util.HashMap;
import java.util.Map;

public class FakeTeamDao implements TeamDao{
    private final Map<Integer, String> database = new HashMap<>();

    @Override
    public void insertTeam() {
        database.put(1, CampType.CHO.getName());
        database.put(2, CampType.HAN.getName());
    }
}
