package janggi.dao.turn;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dto.TurnDto;
import janggi.domain.players.Team;
import janggi.domain.players.Turn;
import org.junit.jupiter.api.Test;

class TurnManagerTest {

    private final TurnDao turnDao = new FakeTurnDao(new TurnDto(Team.CHO));
    private final TurnManager turnManager = new TurnManager(turnDao);

    @Test
    void 초기화한다() {
        // Given

        // When
        turnManager.initialize();

        // Then
        assertThat(turnDao.selectCurrentTeam()).isEqualTo(Turn.initialize(Team.CHO));
    }

    @Test
    void 현재_차례를_반환한다() {
        // Given
        turnManager.initialize();

        // When & Then
        assertThat(turnManager.findCurrentTurn()).isEqualTo(Turn.initialize(Team.CHO));
    }

    @Test
    void 현재_턴으로_업데이트한다() {
        // Given
        turnManager.initialize();
        turnManager.updateCurrentTurn(Turn.initialize(Team.HAN));

        // When & Then
        assertThat(turnManager.findCurrentTurn()).isEqualTo(Turn.initialize(Team.HAN));
    }
}
