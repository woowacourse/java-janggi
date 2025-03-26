package janggiGame.state.Finished;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import janggiGame.Dot;
import janggiGame.arrangement.InnerElephantStrategy;
import janggiGame.arrangement.OuterElephantStrategy;
import janggiGame.state.GameResult;
import janggiGame.state.State;
import java.util.stream.Stream;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FinishedTest {

    @DisplayName("종료 상태일 때 각 게임 결과를 알맞게 반환한다")
    @ParameterizedTest
    @MethodSource("provideFinishedAndExpected")
    void getGameResult(State finished, GameResult expected) {
        // when
        GameResult actual = finished.getGameResult();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    public static Stream<Arguments> provideFinishedAndExpected() {
        return Stream.of(
                Arguments.of(new ChoWin(), GameResult.CHO_WIN),
                Arguments.of(new HanWin(), GameResult.HAN_WIN),
                Arguments.of(new Draw(), GameResult.DRAW)
        );
    }

    @DisplayName("Finished는 끝난 상태이다.")
    @ParameterizedTest
    @MethodSource("provideFinishedInstances")
    void isFinished(State finished) {
        // when
        boolean actual = finished.isFinished();

        // then
        assertThat(actual).isTrue();
    }

    public static Stream<Arguments> provideFinishedInstances() {
        return Stream.of(
                Arguments.of(new Draw(), new HanWin(), new ChoWin())
        );
    }

    @DisplayName("끝난 상태에서 호출할 수 없는 메서드를 호출 시 예외가 발생한다")
    @ParameterizedTest
    @MethodSource("provideInvalidMethod")
    void test(ThrowingCallable throwingCallable) {
        // when // then
        assertThatCode(throwingCallable)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    public static Stream<Arguments> provideInvalidMethod() {
        State finished = new Draw();

        return Stream.of(
                Arguments.of((ThrowingCallable) () -> finished.takeTurn(Dot.getInstanceBy(1, 1), Dot.getInstanceBy(2, 2))),
                Arguments.of((ThrowingCallable) finished::skipTurn),
                Arguments.of((ThrowingCallable) () -> finished.arrangePieces(new OuterElephantStrategy(), new InnerElephantStrategy())),
                Arguments.of((ThrowingCallable) finished::getGameScore),
                Arguments.of((ThrowingCallable) finished::getPieces),
                Arguments.of((ThrowingCallable) finished::getCurrentDynasty)
        );
    }
}