package persistence.repository;

import domain.GameStatus;
import domain.PieceProperty;
import domain.PieceType;
import domain.Position;
import domain.Team;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import persistence.entity.GameState;
import persistence.entity.PieceState;

class InMemoryGameStateRepositoryTest {

    private final PieceProperty horseGreenProperty = new PieceProperty(PieceType.HORSE, Team.GREEN);
    private final PieceProperty soldierGreenProperty = new PieceProperty(PieceType.SOLDIER, Team.GREEN);
    private final PieceProperty generalGreenProperty = new PieceProperty(PieceType.GENERAL, Team.GREEN);

    @Test
    @DisplayName("게임 상태 저장/조회 검증 테스트")
    void save_and_load_game_state_test() {
        GameStateRepository repository = new InMemoryGameStateRepository();

        Position horsePosition = new Position(9, 1);
        Position soldierPosition = new Position(6, 0);
        Position generalPosition = new Position(8, 4);

        PieceState horseState = new PieceState(horseGreenProperty, horsePosition);
        PieceState soldierState = new PieceState(soldierGreenProperty, soldierPosition);
        PieceState generalState = new PieceState(generalGreenProperty, generalPosition);
        GameState expected = new GameState(List.of(horseState, soldierState, generalState), GameStatus.GREEN_PLAYER_TURN);

        repository.save(expected);
        Optional<GameState> actual = repository.load();

        Assertions.assertThat(actual.get()).isEqualTo(expected);
    }

}
