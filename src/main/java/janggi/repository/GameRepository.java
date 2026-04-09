package janggi.repository;

import janggi.domain.JanggiGame;
import janggi.dto.GameInfo;

import java.util.List;

public interface GameRepository {

    List<GameInfo> findAllGames();

    long createGame(JanggiGame game);

    void updateGame(long gameId, JanggiGame game);

    JanggiGame getById(long gameId);
}
