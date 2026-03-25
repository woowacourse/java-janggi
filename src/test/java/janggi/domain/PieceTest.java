package janggi.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.piece.Cannon;
import janggi.domain.piece.Piece;
import janggi.domain.team.TeamType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class PieceTest {


    @Nested
    @DisplayName("특정 팀 기물 여부 판정 테스트")
    class belongsToTeam {

        @Test
        @DisplayName("특정 팀에 해당하는 경우")
        void success_1() {
            TeamType teamType = TeamType.RED;
            Piece cannon = new Cannon(teamType);
            boolean expected = true;

            boolean actual = cannon.belongsToTeam(teamType);

            assertThat(actual).isEqualTo(expected);
        }

        @Test
        @DisplayName("특정 팀에 해당하지 않는 경우")
        void success_2() {
            TeamType teamType = TeamType.RED;
            TeamType otherTeamType = TeamType.BLUE;
            Piece cannon = new Cannon(otherTeamType);
            boolean expected = false;

            boolean actual = cannon.belongsToTeam(teamType);

            assertThat(actual).isEqualTo(expected);
        }
    }
}
