package janggi.domain.piece;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.team.TeamType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PieceTest {


    @Nested
    @DisplayName("다른 기물과 같은 팀 여부 판정 테스트")
    class isOnSameTeamAs {

        @Test
        @DisplayName("같은 팀인 경우")
        void success_1() {
            Piece me = new Cannon(TeamType.RED);
            Piece other = new Cannon(TeamType.RED);
            boolean expected = true;

            boolean actual = me.isOnSameTeamAs(other);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("다른 팀인 경우")
        void success_2() {
            Piece me = new Cannon(TeamType.RED);
            Piece other = new Cannon(TeamType.BLUE);
            boolean expected = false;

            boolean actual = me.isOnSameTeamAs(other);

            assertThat(actual).isEqualTo(expected);
        }
    }
}
