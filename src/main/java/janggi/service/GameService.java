package janggi.service;

import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.domain.turn.TurnManager;
import janggi.entity.GameStateEntity;
import janggi.repository.GameStateRepository;
import java.util.List;

public class GameService {

    private final GameStateRepository gameStateRepository;

    public GameService(final GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    public boolean hasGameState(final long id) {
        return gameStateRepository.findById(id).isPresent();
    }

    public GameStateEntity loadOrSaveGameState(final long id) {
        return gameStateRepository.findById(id)
            .orElseGet(() -> gameStateRepository.save(
                GameStateEntity.from(1, List.of(TeamType.BLUE, TeamType.RED))));
    }

    public long modifyGameState(final long id, final TurnManager turnManager) {
        final List<TeamType> teamQueue = turnManager.getTeams().stream()
            .map(Team::getTeamType)
            .toList();
        final GameStateEntity gameStateEntity = GameStateEntity.from(id, turnManager.getTurnTaken(),
            teamQueue);

        return gameStateRepository.update(gameStateEntity);
    }

    public boolean removeGameState(final long id) {
        return gameStateRepository.deleteById(id);
    }
}
