package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("점수 결과(ScoreResult) 테스트")
class ScoreResultTest {

    @DisplayName("한나라 점수가 높으면 isHanWin이 true를 반환한다.")
    @Test
    void isHanWin() {
        ScoreResult result = new ScoreResult(Score.of(73.5), Score.of(72.0));

        assertThat(result.isHanWin()).isTrue();
    }

    @DisplayName("초나라 점수가 높으면 isChoWin이 true를 반환한다.")
    @Test
    void isChoWin() {
        ScoreResult result = new ScoreResult(Score.of(60.0), Score.of(72.0));

        assertThat(result.isChoWin()).isTrue();
    }

    @DisplayName("두 점수가 같으면 isDraw가 true를 반환한다.")
    @Test
    void isDraw() {
        ScoreResult result = new ScoreResult(Score.of(72.0), Score.of(72.0));

        assertThat(result.isDraw()).isTrue();
    }

    @DisplayName("한나라가 이기면 isChoWin과 isDraw는 false를 반환한다.")
    @Test
    void isHanWin_OthersFalse() {
        ScoreResult result = new ScoreResult(Score.of(73.5), Score.of(72.0));

        assertAll(
                () -> assertThat(result.isChoWin()).isFalse(),
                () -> assertThat(result.isDraw()).isFalse()
        );
    }
}
