package domain.setup;

import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Arrangements 클래스 테스트")
class ArrangementsTest {

    @Test
    @DisplayName("arrangeFor: 배치된 팀은 올바른 Arrangement를 반환한다")
    void arrangeForAssignedTeamReturnsCorrectArrangement() {
        Arrangements arrangements = new Arrangements()
                .assignArrangement(Team.CHO, Arrangement.SANGMAMASANG);

        assertThat(arrangements.arrangeFor(Team.CHO)).isEqualTo(Arrangement.SANGMAMASANG);
    }
}
