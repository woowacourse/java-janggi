package janggi.domain.board;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import janggi.domain.setup.InnerElephantSetupPolicy;
import janggi.domain.team.BlueTeam;
import janggi.domain.team.RedTeam;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BoardGeneratorTest {

    @Test
    @DisplayName("보드판 및 기물 생성 테스트")
    void success() {
        // given
        RedTeam redTeam = new RedTeam(new InnerElephantSetupPolicy());
        BlueTeam blueTeam = new BlueTeam(new InnerElephantSetupPolicy());
        final int expected = 32;

        // when
        Board board = BoardGenerator.generate(redTeam, blueTeam);

        // then
        assertThat(board).extracting("positionPieceMap")
            .asInstanceOf(InstanceOfAssertFactories.MAP)
            .hasSize(expected);
    }
}
