package domain;

import domain.piece.Cannon;
import domain.piece.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class PieceTest {

    @Test
    void 소속팀이_달라도_기물은_동등하다() {
        Cannon cannonCho = new Cannon(Team.CHO);
        Cannon cannonHan = new Cannon(Team.HAN);

        Assertions.assertThat(cannonCho)
                .isEqualTo(cannonHan);
    }

}
