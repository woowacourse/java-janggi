package domain.dao;

import domain.JanggiGame;
import domain.piece.Team;
import java.util.List;

public interface GamesDao {

    JanggiGame add(final String name, final Team team);

    JanggiGame findByName(final String name);

    List<String> findAllName();

    Long countAll();

}
