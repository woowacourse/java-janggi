package piece;

import static janggi.piece.Team.CHO;
import static janggi.piece.Team.HAN;
import static org.assertj.core.api.Assertions.assertThat;

import janggi.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TeamTest {

    @Test
    @DisplayName("턴을 넘길 수 있다.")
    void nextTurnTest(){
        // given
        Team currentTeam = CHO;

        // when - then
        assertThat(Team.next(currentTeam)).isEqualTo(HAN);
    }
}
