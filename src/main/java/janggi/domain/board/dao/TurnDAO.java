package janggi.domain.board.dao;

import janggi.domain.setting.CampType;

public interface TurnDAO {
    void insertQuery(final CampType campType);
    void updateQuery(final CampType campType);
    CampType selectQuery();
    void dropTurnTable();
}
