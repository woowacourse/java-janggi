package janggi.domain.state;

import janggi.domain.piece.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GameStateFactoryTest {

    static Stream<Arguments> stateProvider() {
        return Stream.of(
                Arguments.of("DRAW", "CHO", false, Draw.class),
                Arguments.of("CHECKMATE", "CHO", false, Checkmate.class),
                Arguments.of("GIVE UP", "CHO", false, GiveUp.class),
                Arguments.of("CHO TURN", "CHO", true, ChoTurn.class),
                Arguments.of("HAN TURN", "HAN", true, HanTurn.class)
        );
    }

    @DisplayName("올바른 상태 타입이면 게임 상태를 반환한다")
    @ParameterizedTest
    @MethodSource("stateProvider")
    void create_CorrectStateType_ReturnCorrectState(String stateType, String turn, boolean ongoing, Class<? extends Piece> stateClass) {
        assertThat(GameStateFactory.create(turn, ongoing, stateType))
                .isInstanceOf(stateClass);
    }

    @DisplayName("올바르지 않은 종료 상태 타입이면 예외를 발생한다")
    @Test
    void create_CorrectStateType_ReturnCorrectState() {
        assertThatThrownBy(() -> GameStateFactory.create("HAN", false, "INCORRECT"))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("지원되지 않는 게임 종료 상태입니다.");
    }
}
