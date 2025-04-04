package janggi.service.fake;

import janggi.domain.board.dao.TeamDAO;
import janggi.domain.setting.CampType;
import java.util.HashMap;
import java.util.Map;

public class FakeTeamTable implements TeamDAO {

    private final Map<Integer, String> database = new HashMap<>();

    @Override
    public void insertTeam() {
        database.put(1, CampType.CHO.getName());
        database.put(2, CampType.HAN.getName());
    }

    @Override
    public void dropTeamTable() {
        database.clear();
    }
}
