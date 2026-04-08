package janggi.sevice;

import janggi.domain.Location;
import janggi.domain.state.GameContext;
import janggi.domain.strategy.ArrangementStrategy;
import janggi.domain.strategy.IntersectionInitializer;
import janggi.sevice.dto.GameInformation;
import java.util.List;

public interface JanggiService {
    List<Long> findActiveGameIds();

    GameInformation loadGameInformation(Long gameId, IntersectionInitializer intersectionInitializer);

    GameInformation createGame(List<ArrangementStrategy> strategies, IntersectionInitializer intersectionInitializer);

    void movePiece(GameInformation gameInformation, Location from, Location to, GameContext gameContext);

    void endGame(Long gameId);
}
