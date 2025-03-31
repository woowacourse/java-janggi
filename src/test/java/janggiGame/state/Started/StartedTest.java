package janggiGame.state.Started;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.arrangement.ArrangementStrategy;
import janggiGame.arrangement.InnerElephantStrategy;
import janggiGame.position.Position;
import janggiGame.state.Running.ChoTurn;
import janggiGame.state.State;
import java.util.stream.Stream;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StartedTest {

    @DisplayName("시작 상태에서 호출할 수 없는 메서드를 호출 시 예외가 발생한다")
    @ParameterizedTest
    @MethodSource("provideInvalidMethod")
    void test(ThrowingCallable throwingCallable) {
        // when // then
        assertThatCode(throwingCallable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    public static Stream<Arguments> provideInvalidMethod() {
        State started = new Started();

        return Stream.of(
                Arguments.of((ThrowingCallable) () -> started.takeTurn(
                        Position.getInstanceBy(1, 1), Position.getInstanceBy(2, 2))),
                Arguments.of((ThrowingCallable) started::skipTurn),
                Arguments.of((ThrowingCallable) started::getGameResult),
                Arguments.of((ThrowingCallable) started::getGameScore),
                Arguments.of((ThrowingCallable) started::getPieces),
                Arguments.of((ThrowingCallable) started::getCurrentDynasty)
        );
    }

    @DisplayName("시작상태는 끝나지 않은 상태이다")
    @Test
    void isFinished() {
        // given
        State started = new Started();

        // when
        boolean actual = started.isFinished();

        // then
        assertThat(actual).isFalse();
    }

    @DisplayName("시작상태에서 기물을 배치하면 초나라의 턴으로 넘어간다")
    @Test
    void startedToChoTurn() {
        // given
        State started = new Started();
        ArrangementStrategy strategy = new InnerElephantStrategy();

        // when
        State actual = started.arrangePieces(strategy, strategy);

        // then
        assertThat(actual).isInstanceOf(ChoTurn.class);
    }
}
