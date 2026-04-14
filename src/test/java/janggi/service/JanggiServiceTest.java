package janggi.service;

import janggi.repository.FakeJanggiRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class JanggiServiceTest {

    @DisplayName("항복 요청 시 그에 맞는 게임 결과를 반환한다")
    @Test
    void giveUpGame_ReturnCorrectGameResult() {
        JanggiService janggiService = new JanggiService(new FakeJanggiRepository());
        Long id = janggiService.start(1, 1);
        assertThat(janggiService.giveUpGame(id).getDescription()).isEqualTo("한 승리");
    }

    @DisplayName("게임이 끝났다면 초와 한의 점수를 비교해서 게임 결과를 반환한다")
    @Test
    void processDrawGameResult_isGameFinished_ReturnGameResult() {
        JanggiService janggiService = new JanggiService(new FakeJanggiRepository());
        Long id = janggiService.start(1, 1);
        assertThat(janggiService.drawGame(id).getDescription()).isEqualTo("무승부");
    }

    @DisplayName("게임이 끝나지 않았는데 무승부 게임 결과 처리 기능을 사용하면 예외가 발생한다")
    @Test
    void processDrawGameResult_isNotGameFinished_ReturnException() {
        JanggiService janggiService = new JanggiService(new FakeJanggiRepository());
        Long id = janggiService.start(1, 1);
        assertThat(janggiService.checkMatchResult(id)).isEmpty();
    }
}
