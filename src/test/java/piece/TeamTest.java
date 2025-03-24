package piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class TeamTest {

    @Test
    void 팀은_적팀을_반환할_수_있다() {
        Team redTeam = Team.RED;
        Team blueTeam = Team.BLUE;

        assertThat(redTeam.opposite()).isEqualTo(blueTeam);
        assertThat(blueTeam.opposite()).isEqualTo(redTeam);
    }

    @Test
    void 지원하지_않는_팀에_대해_exception을_던진다() {
        Team unsupportedTeam = null;

        assertThatThrownBy(() -> unsupportedTeam.opposite())
                .isInstanceOf(IllegalStateException.class);
    }
}

