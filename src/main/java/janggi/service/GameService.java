package janggi.service;

import janggi.domain.team.TeamType;
import janggi.domain.turn.TurnManager;
import janggi.entity.GameEntity;
import janggi.mapper.TurnManagerMapper;
import janggi.repository.GameRepository;
import java.util.List;

public class GameService {

    private final GameRepository gameRepository;

    public GameService(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public boolean hasGame(final long id) {
        return gameRepository.findById(id).isPresent();
    }

    public GameEntity loadOrSaveGame(final long id) {
        return gameRepository.findById(id)
            .orElseGet(() -> gameRepository.save(
                GameEntity.from("게임 1", 1, List.of(TeamType.BLUE, TeamType.RED))));
    }

    public void updateGame(final long id, final TurnManager turnManager) {
        gameRepository.updateById(id,
            TurnManagerMapper.toEntity(turnManager.getTurnTaken(), turnManager.getTeams()));
    }

    public boolean removeGame(final long id) {
        return gameRepository.deleteById(id);
    }
}
