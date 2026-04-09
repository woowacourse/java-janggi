package janggi.repository;

import janggi.JanggiGame2;

import java.util.List;

public interface GameRepository2 {

    List<GameInfo2> findAllGames();

    long createGame(JanggiGame2 game);

    void updateGame(long gameId, JanggiGame2 game);

    JanggiGame2 getById(long gameId);
}
