package save;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import move.ChaMoveBehavior;
import move.FoMoveBehavior;
import move.GungMoveBehavior;
import move.JanggiMoveBehavior;
import move.JolMoveBehavior;
import move.MaMoveBehavior;
import move.SaMoveBehavior;
import move.SangMoveBehavior;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import piece.PieceType;

class JanggiTypeMoveBehaviorMapperTest {

    @DisplayName("피스 타입에 따라 올바른 이동 행동을 반환해야 한다")
    @ParameterizedTest
    @MethodSource("pieceTypeAndMoveBehaviors")
    void shouldReturnCorrectMoveBehaviorForPieceType(String pieceType,
                                                     Class<? extends JanggiMoveBehavior> expectedBehaviorClass) {
        JanggiMoveBehavior behavior = JanggiTypeMoveBehaviorMapper.from(pieceType);

        assertThat(behavior).isInstanceOf(expectedBehaviorClass);
    }

    @DisplayName("지원하지 않는 피스 타입에 대해 예외를 던져야 한다")
    @Test
    void shouldThrowExceptionForUnsupportedPieceType() {
        String notSupportedType = "테스트";

        assertThatThrownBy(() -> JanggiTypeMoveBehaviorMapper.from(notSupportedType))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> pieceTypeAndMoveBehaviors() {
        return Stream.of(
                Arguments.of(PieceType.CHA.name(), ChaMoveBehavior.class),
                Arguments.of(PieceType.FO.name(), FoMoveBehavior.class),
                Arguments.of(PieceType.JOL.name(), JolMoveBehavior.class),
                Arguments.of(PieceType.GUNG.name(), GungMoveBehavior.class),
                Arguments.of(PieceType.MA.name(), MaMoveBehavior.class),
                Arguments.of(PieceType.SANG.name(), SangMoveBehavior.class),
                Arguments.of(PieceType.SA.name(), SaMoveBehavior.class)
        );
    }
}
