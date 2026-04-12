package janggi.repository;

import janggi.domain.JanggiGame;
import janggi.dto.GameInfo;

import java.util.List;
import java.util.Optional;

public interface GameRepository {

    List<GameInfo> findAllGames();

    long createGame(JanggiGame game);

    void saveGameState(long gameId, JanggiGame game);

    Optional<JanggiGame> getById(long gameId);

    boolean deleteGame(long gameId);
}
