package repository.mapper;

import domain.GameContext;
import domain.GameId;
import domain.GameState;
import domain.Turn;
import domain.piece.Team;
import repository.entity.GameEntity;

public final class GameContextMapper {
    public GameContext toDomain(GameEntity entity) {
        return new GameContext(
                new Turn(Team.valueOf(entity.turn())),
                GameState.valueOf(entity.state())
        );
    }

    public GameEntity toNewEntity(GameContext context) {
        return new GameEntity(
                null,
                context.getTurn().turnOwnTeam().name(),
                context.getGameState().name()
        );
    }

    public GameEntity toEntity(GameId id, GameContext newContext) {
        return new GameEntity(
                id.value(),
                newContext.getTurn().turnOwnTeam().name(),
                newContext.getGameState().name()
        );
    }
}
