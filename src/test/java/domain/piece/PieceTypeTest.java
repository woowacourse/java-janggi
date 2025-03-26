package domain.piece;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

class PieceTypeTest {

    @Nested
    class ValidCases {

        @DisplayName("PieceType Enum name에 해당하는 PieceType을 반환한다.")
        @ParameterizedTest
        @MethodSource("providePieceTypeNameAndPieceType")
        void from(String name, PieceType pieceType) {
            assertThat(PieceType.from(name)).isEqualTo(pieceType);
        }

        static Stream<Arguments> providePieceTypeNameAndPieceType() {
            return Stream.of(
                    Arguments.of("cannon", PieceType.CANNON),
                    Arguments.of("chariot", PieceType.CHARIOT),
                    Arguments.of("elephant", PieceType.ELEPHANT),
                    Arguments.of("general", PieceType.GENERAL),
                    Arguments.of("guard", PieceType.GUARD),
                    Arguments.of("horse", PieceType.HORSE),
                    Arguments.of("zzu", PieceType.ZZU)
            );
        }
    }

    @Nested
    class InvalidCases {

        @DisplayName("해당하는 PieceType name이 없다면 예외가 발생한다.")
        @ParameterizedTest
        @ValueSource(strings = "pho, pig, human, hotteok")
        void from(String name) {
            assertThatThrownBy(() -> PieceType.from(name))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("해당하는 PieceType이 없습니다.");
        }
    }
}
