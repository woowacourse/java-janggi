package domain.game;

import domain.piece.Camp;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class GameStateTest {
    @Test
    @DisplayName("Finished 상태에서는 이동 검증 시 무조건 예외가 발생한다.")
    void throwException_When_finishedState() {
        Camp winner = Camp.CHO;
        GameState finished = new Finished(winner);

        assertThatThrownBy(finished::validateMovable)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("InProgress 상태에서는 이동 검증 시 아무 일도 일어나지 않는다.")
    void doesNotThrowException_When_InProgressState() {
        GameState inProgress = new InProgress();

        assertThatCode(inProgress::validateMovable)
                .doesNotThrowAnyException();
    }
}
