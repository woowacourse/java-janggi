package domain.dao;

import domain.piece.Team;
import java.util.List;

public interface GameDao {

    Long add(final String name, final Team team);

    Long findIdByName(final String name);

    List<String> findAllName();

    Long countAll();

    Team findTurn(Long gameId);

    void changeTurn(Long gameId, final Team turn);
}
