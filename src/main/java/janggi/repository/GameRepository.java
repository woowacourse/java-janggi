package janggi.repository;

import janggi.domain.Game;
import janggi.dto.TurnDto;

public interface GameRepository {
    long saveNewGame(Game game);

    Game enterGame(long roomId);

    void saveMove(long roomId, Game game, TurnDto turnDto);
}