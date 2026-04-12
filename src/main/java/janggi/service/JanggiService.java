package janggi.service;

import janggi.domain.board.Location;
import janggi.domain.strategy.arrangement.ArrangementStrategy;
import janggi.domain.strategy.intersection.IntersectionInitializer;
import janggi.service.dto.GameInformation;
import java.util.List;

public interface JanggiService {
    List<Long> findActiveGameIds();

    GameInformation loadGameInformation(Long gameId, IntersectionInitializer intersectionInitializer);

    GameInformation createGame(List<ArrangementStrategy> strategies, IntersectionInitializer intersectionInitializer);

    void movePiece(GameInformation gameInformation, Location from, Location to);

    void endGame(Long gameId);
}
