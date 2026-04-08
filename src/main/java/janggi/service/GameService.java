package janggi.service;

import janggi.domain.game.GameStatus;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.domain.turn.TurnManager;
import janggi.infrastructure.entity.GameEntity;
import janggi.global.Pair;
import janggi.infrastructure.mapper.TurnManagerMapper;
import janggi.infrastructure.repository.GameRepository;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class GameService {

    private final GameRepository gameRepository;

    public GameService(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Map<Long, String> getAllGamesInProgress(final int limit) {
        final List<Long> ids = gameRepository.findByStatusOrderByLatest(GameStatus.IN_PROGRESS,
            limit);
        final Map<Long, String> idGameNameMap = new LinkedHashMap<>();
        ids.stream()
            .map(gameRepository::findById)
            .flatMap(Optional::stream)
            .forEach(gameEntity -> idGameNameMap.put(gameEntity.id(), gameEntity.name()));

        return idGameNameMap;
    }

    public long createNewGame(final String name, final TurnManager turnManager) {
        final List<TeamType> teamQueue = turnManager.getTeams()
            .stream()
            .map(Team::getTeamType)
            .toList();
        final GameEntity gameEntity = GameEntity.from(name, turnManager.getTurnTaken(), teamQueue,
            GameStatus.IN_PROGRESS);

        return gameRepository.save(gameEntity);
    }

    public Pair<String, TurnManager> loadGame(final long id) {
        return gameRepository.findById(id)
            .map(gameEntity ->
                new Pair<>(gameEntity.name(), TurnManagerMapper.toDomain(gameEntity)))
            .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 게임이 존재하지 않습니다."));
    }

    public void updateGame(final long id, final TurnManager turnManager) {
        gameRepository.updateById(id,
            TurnManagerMapper.toEntity(turnManager.getTurnTaken(), turnManager.getTeams(),
                GameStatus.IN_PROGRESS));
    }

    public void closeGame(final long id) {
        gameRepository.updateStatusById(id, GameStatus.CLOSED);
    }
}
