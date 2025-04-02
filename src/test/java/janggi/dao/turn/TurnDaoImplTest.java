package janggi.dao.turn;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.dto.TurnDto;
import janggi.piece.players.Team;
import janggi.piece.players.Turn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TurnDaoImplTest {

    private final TurnDao turnDao = TestTurnDaoImpl.getDaoImpl();

    @BeforeEach
    void setUp() {
        turnDao.deleteAll();
    }

    @Test
    void 현재_팀을_초기화한다() {
        // Given

        // When
        turnDao.initialize(new TurnDto(Team.CHO));

        // Then
        assertThat(turnDao.selectCurrentTeam().getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 현재_팀을_조회한다() {
        // Given
        turnDao.initialize(new TurnDto(Team.CHO));

        // When
        final Turn turn = turnDao.selectCurrentTeam();

        // Then
        assertThat(turn.getTeam()).isEqualTo(Team.CHO);
    }

    @Test
    void 현재_팀을_갱신한다() {
        // Given
        turnDao.initialize(new TurnDto(Team.CHO));

        // When
        turnDao.updateTurn(Team.HAN);

        // Then
        assertThat(turnDao.selectCurrentTeam().getTeam()).isEqualTo(Team.HAN);
    }
}
