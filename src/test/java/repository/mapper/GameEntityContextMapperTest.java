package repository.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import domain.GameContext;
import domain.GameId;
import domain.GameState;
import domain.Turn;
import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.entity.GameEntity;

class GameEntityContextMapperTest {

    private final GameContextMapper mapper = new GameContextMapper();

    @Test
    @DisplayName("Entity → Domain 변환")
    void toDomain_success() {
        GameEntity entity = new GameEntity(1L, "HAN", "END");

        GameContext domain = mapper.toDomain(entity);

        assertThat(domain.getTurn().turnOwnTeam()).isEqualTo(Team.HAN);
        assertThat(domain.getGameState()).isEqualTo(GameState.END);
    }

    @Test
    @DisplayName("Domain → Entity(신규 저장) 변환")
    void toNewEntity_success() {
        GameContext domain = new GameContext(new Turn(Team.CHO), GameState.PLAYING);

        GameEntity entity = mapper.toNewEntity(domain);

        assertThat(entity.id()).isNull();
        assertThat(entity.turn()).isEqualTo("CHO");
        assertThat(entity.state()).isEqualTo("PLAYING");
    }

    @Test
    @DisplayName("Domain → Entity -> toDomain을 하면 동일한 값을 가진다")
    void toEntity_roundTrip() {
        GameContext original = new GameContext(new Turn(Team.HAN), GameState.PLAYING);
        long id = 99L;
        GameId testId = new GameId(id);

        GameEntity entity = mapper.toEntity(testId, original);
        GameContext restored = mapper.toDomain(entity);

        assertThat(entity.id()).isEqualTo(id);
        assertThat(restored.getTurn().turnOwnTeam()).isEqualTo(original.getTurn().turnOwnTeam());
        assertThat(restored.getGameState()).isEqualTo(original.getGameState());
    }
}
