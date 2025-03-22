package piece;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import team.Team;

public class PieceTest {

    @Nested
    @DisplayName("같은 팀인 지 판별하는 테스트")
    class IsSameTeamTest {

        @Test
        @DisplayName("다른 기물과 같은 팀이면 true를 반환한다.")
        void test1() {
            // given
            Piece piece1 = new Jol(Team.HAN);
            Piece piece2 = new Jol(Team.HAN);

            // when
            boolean isSameTeam = piece1.isSameTeam(piece2);

            // then
            assertThat(isSameTeam).isTrue();
        }

        @Test
        @DisplayName("다른 기물과 다른 팀이면 false를 반환한다.")
        void test2() {
            // given
            Piece piece1 = new Jol(Team.HAN);
            Piece piece2 = new Jol(Team.CHO);

            // when
            boolean isSameTeam = piece1.isSameTeam(piece2);

            // then
            assertThat(isSameTeam).isFalse();
        }
    }
}
