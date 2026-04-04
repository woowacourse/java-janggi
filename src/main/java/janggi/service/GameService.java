package janggi.service;

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

    public Optional<Long> getLatestGameId() {
        final List<Long> ids = gameRepository.findAllIdsOrderByLatest(1);
        if (ids.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(ids.getFirst());
    }

    public long createNewGame(final String name) {
        final GameEntity gameEntity = GameEntity.from(name, 1,
            List.of(TeamType.BLUE, TeamType.RED));

        return gameRepository.save(gameEntity);
    }

    public GameEntity loadGame(final long id) {
        return gameRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 게임이 존재하지 않습니다."));
    }

    public void updateGame(final long id, final TurnManager turnManager) {
        gameRepository.updateById(id,
            TurnManagerMapper.toEntity(turnManager.getTurnTaken(), turnManager.getTeams()));
    }

    public boolean removeGame(final long id) {
        return gameRepository.deleteById(id);
    }
}
