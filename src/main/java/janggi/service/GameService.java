package janggi.service;

import janggi.domain.team.Team;
import janggi.domain.team.TeamType;
import janggi.domain.turn.TurnManager;
import janggi.entity.GameEntity;
import janggi.repository.GameRepository;
import java.util.List;

public class GameService {

    private final GameRepository gameRepository;

    public GameService(final GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public boolean hasGameState(final long id) {
        return gameRepository.findById(id).isPresent();
    }

    public GameEntity loadOrSaveGameState(final long id) {
        return gameRepository.findById(id)
            .orElseGet(() -> gameRepository.save(
                GameEntity.from("게임 1",1,  List.of(TeamType.BLUE, TeamType.RED))));
    }

    public long modifyGameState(final long id, final String name, final TurnManager turnManager) {
        final List<TeamType> teamQueue = turnManager.getTeams().stream()
            .map(Team::getTeamType)
            .toList();
        final GameEntity gameEntity = GameEntity.from(id, name, turnManager.getTurnTaken(),
            teamQueue);

        return gameRepository.update(gameEntity);
    }

    public boolean removeGameState(final long id) {
        return gameRepository.deleteById(id);
    }
}
