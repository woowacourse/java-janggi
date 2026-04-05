package janggi.service;

import janggi.domain.game.GameStatus;
import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.domain.turn.TurnManager;
import janggi.entity.GameEntity;
import janggi.mapper.TurnManagerMapper;
import janggi.repository.GameRepository;
import java.util.List;
import java.util.Optional;

public class GameService {

    private final GameRepository gameRepository;

    public GameService(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<GameEntity> getAllGamesInProgress(final int limit) {
        final List<Long> ids = gameRepository.findByStatusOrderByLatest(GameStatus.IN_PROGRESS,
            limit);

        return ids.stream()
            .map(gameRepository::findById)
            .flatMap(Optional::stream)
            .toList();
    }

    public Optional<Long> getLatestGameId() {
        final List<Long> ids = gameRepository.findAllIdsOrderByLatest(1);
        if (ids.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ids.getFirst());
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

    public GameEntity loadGame(final long id) {
        return gameRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 게임이 존재하지 않습니다."));
    }

    public void updateGame(final long id, final TurnManager turnManager) {
        gameRepository.updateById(id,
            TurnManagerMapper.toEntity(turnManager.getTurnTaken(), turnManager.getTeams(),
                GameStatus.IN_PROGRESS));
    }

    public boolean removeGame(final long id) {
        return gameRepository.deleteById(id);
    }
}
