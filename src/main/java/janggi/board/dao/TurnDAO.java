package janggi.board.dao;

import janggi.setting.CampType;

public interface TurnDAO {
    void insertQuery(final CampType campType);
    void updateQuery(final CampType campType);
    CampType selectQuery();
    void dropTurnTable();
}
