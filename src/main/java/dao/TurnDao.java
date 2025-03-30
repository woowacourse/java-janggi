package dao;

import domain.type.JanggiTeam;

public interface TurnDao {
    void deleteAll();

    void save(JanggiTeam team);

    JanggiTeam findTurn();
}
