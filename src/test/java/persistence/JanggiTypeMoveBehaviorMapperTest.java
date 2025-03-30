package persistence;

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

    @ParameterizedTest
    @MethodSource("pieceTypeAndMoveBehaviors")
    void 피스_타입에_따라_올바른_이동_행동을_반환해야_한다(String pieceType,
                                      JanggiMoveBehavior expectedBehaviorClass) {
        JanggiMoveBehavior behavior = JanggiTypeMoveBehaviorMapper.from(pieceType);

        assertThat(behavior).isEqualTo(expectedBehaviorClass);
    }

    @DisplayName("지원하지 않는 피스 타입에 대해 예외를 던져야 한다")
    @Test
    void 지원하지_않는_피스_타입에_대해_예외를_던져야_한다() {
        String notSupportedType = "테스트";

        assertThatThrownBy(() -> JanggiTypeMoveBehaviorMapper.from(notSupportedType))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> pieceTypeAndMoveBehaviors() {
        return Stream.of(
                Arguments.of(PieceType.CHA.name(), new ChaMoveBehavior()),
                Arguments.of(PieceType.FO.name(), new FoMoveBehavior()),
                Arguments.of(PieceType.JOL.name(), new JolMoveBehavior()),
                Arguments.of(PieceType.GUNG.name(), new GungMoveBehavior()),
                Arguments.of(PieceType.MA.name(), new MaMoveBehavior()),
                Arguments.of(PieceType.SANG.name(), new SangMoveBehavior()),
                Arguments.of(PieceType.SA.name(), new SaMoveBehavior())
        );
    }
}
