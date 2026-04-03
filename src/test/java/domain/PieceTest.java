package domain;

import domain.piece.Cannon;
import domain.team.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    @DisplayName("소속팀이 달라도 기물은 동등하다.")
    void should_be_equal_even_if_team_is_different() {
        Cannon cannonCho = new Cannon(Team.CHO);
        Cannon cannonHan = new Cannon(Team.HAN);

        Assertions.assertThat(cannonCho)
                .isEqualTo(cannonHan);
    }

}
