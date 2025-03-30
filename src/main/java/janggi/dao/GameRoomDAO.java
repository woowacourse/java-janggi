package janggi.dao;

import janggi.domain.Team;
import java.util.List;

public interface GameRoomDAO {

    List<String> findAllNames();

    Team findTurn(String gameRoomName);

    boolean exist(String gameRoomName);

    void create(String gameRoomName);

    void update(String gameRoomName, Team team);

    void delete(String gameRoomName);
}
