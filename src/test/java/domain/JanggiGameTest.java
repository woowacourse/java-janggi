package domain;

import domain.piece.Team;
import domain.position.Position;
import domain.settingType.SettingType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class JanggiGameTest {
    private static final Position ALLY_JOL = Position.of(4, 1);

    @Test
    void 정상적으로_기물을_움직이면_턴이_바뀌어야_한다() {
        JanggiGame game = JanggiGame.init(SettingType.LEFT, SettingType.LEFT);

        game.executeMove(ALLY_JOL, Position.of(5, 1));

        Assertions.assertThat(game.getTurn()).isEqualTo(Team.HAN);
    }
}
