package janggi.domain;

import janggi.view.dto.GameResult;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JanggiTest {


    @Test
    void 자신의_턴일때_상대의_기물을_선택하면_예외_처리한다() {
        Janggi janggi = Janggi.start(1, 1);

        Assertions.assertThatThrownBy(() -> janggi.validateCamp(Position.of(9, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("자신의 기물만 선택할 수 있습니다.");
    }

    @DisplayName("게임이 끝났다면 초와 한의 점수를 비교해서 게임 결과를 반환한다")
    @Test
    void processDrawGameResult_isGameFinished_ReturnGameResult() {
        Janggi janggi = Janggi.start(1, 1);
        janggi.drawGame();

        GameResult gameResult = janggi.processDrawGameResult();

        Assertions.assertThat(gameResult.getDescription()).isEqualTo("무승부");
    }

    @DisplayName("게임이 끝나지 않았는데 무승부 게임 결과 처리 기능을 사용하면 예외가 발생한다")
    @Test
    void processDrawGameResult_isNotGameFinished_ReturnException() {
        Janggi janggi = Janggi.start(1, 1);

        Assertions.assertThatThrownBy(() -> janggi.processDrawGameResult())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("게임이 종료되지 않았습니다.");
    }

    @DisplayName("항복 요청 시 그에 맞는 게임 결과를 반환한다")
    @Test
    void processGiveUpResult_ReturnCorrectGameResult() {
        Janggi janggi = Janggi.start(1, 1);
        Assertions.assertThat(janggi.processGiveUpResult().getDescription()).isEqualTo("한 승리");
    }

}
