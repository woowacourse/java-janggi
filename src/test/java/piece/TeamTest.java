package piece;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class TeamTest {

    @EnumSource(Team.class)
    @ParameterizedTest
    void 팀은_홍팀과_청팀이_있다(Team team) {
        assertThat(team).isEqualByComparingTo(team);
    }

    @Test
    void 팀의_점수판을_초기화한다() {
        assertThat(Team.initializeScoreBoard())
                .containsExactlyInAnyOrderEntriesOf(
                        Map.of(
                                Team.BLUE, 0.0,
                                Team.RED, 1.5
                        )
                );
    }

}
