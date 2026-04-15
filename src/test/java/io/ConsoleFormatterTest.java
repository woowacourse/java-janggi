package io;

import static org.assertj.core.api.Assertions.assertThat;

import domain.game.GameResult;
import domain.piece.Team;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ConsoleFormatter 클래스 테스트")
class ConsoleFormatterTest {

    @Test
    @DisplayName("게임 결과를 종료 메시지로 포맷한다")
    void formatGameResult() {
        ConsoleFormatter formatter = new ConsoleFormatter();

        String result = formatter.formatGameResult(new GameResult(Team.CHO, 3, 5));

        assertThat(result).contains("게임 종료");
        assertThat(result).contains("승리 팀: 초(CHO)");
        assertThat(result).contains("초(CHO): 3");
        assertThat(result).contains("한(HAN): 5");
    }
}
