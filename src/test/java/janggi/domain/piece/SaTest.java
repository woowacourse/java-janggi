package janggi.domain.piece;

import janggi.domain.position.Position;
import janggi.test_util.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class SaTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("provideValidPositions")
    void 사는_궁의_길을_따라_움직인다(final Position startPosition, final Position newPosition) {
        // given
        final Piece piece = new Piece(PieceType.SA, startPosition);

        // expected
        assertThatCode(() -> piece.move(newPosition, List.of(), List.of()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("provideValidPositions")
    void 거꾸로도_갈_수_있다(final Position newPosition, final Position startPosition) {
        // given
        final Piece piece = new Piece(PieceType.SA, startPosition);

        // expected
        assertThatCode(() -> piece.move(newPosition, List.of(), List.of()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPositions")
    void 그_외의_길을_따라_움직일_수_없다(final Position startPosition, final Position newPosition) {
        // given
        final Piece piece = new Piece(PieceType.SA, startPosition);

        // expected
        assertThatThrownBy(() -> piece.move(newPosition, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    public static Stream<Arguments> provideValidPositions() {
        return Stream.of(
                Arguments.of(POSITION_4_1, POSITION_5_1),
                Arguments.of(POSITION_4_1, POSITION_4_2),
                Arguments.of(POSITION_4_1, POSITION_5_2),
                Arguments.of(POSITION_4_1, POSITION_5_1),
                Arguments.of(POSITION_5_1, POSITION_4_1),
                Arguments.of(POSITION_5_1, POSITION_6_1),
                Arguments.of(POSITION_5_1, POSITION_5_2),
                Arguments.of(POSITION_6_1, POSITION_5_1),
                Arguments.of(POSITION_6_1, POSITION_5_2),
                Arguments.of(POSITION_6_1, POSITION_6_2),
                Arguments.of(POSITION_4_2, POSITION_4_1),
                Arguments.of(POSITION_4_2, POSITION_5_2),
                Arguments.of(POSITION_4_2, POSITION_4_3),
                Arguments.of(POSITION_6_2, POSITION_6_1),
                Arguments.of(POSITION_6_2, POSITION_5_2),
                Arguments.of(POSITION_6_2, POSITION_6_3),
                Arguments.of(POSITION_5_2, POSITION_5_3),
                Arguments.of(POSITION_5_2, POSITION_5_1),
                Arguments.of(POSITION_5_2, POSITION_4_1),
                Arguments.of(POSITION_5_2, POSITION_4_2),
                Arguments.of(POSITION_5_2, POSITION_4_3),
                Arguments.of(POSITION_5_2, POSITION_6_1),
                Arguments.of(POSITION_5_2, POSITION_6_2),
                Arguments.of(POSITION_5_2, POSITION_6_3),
                Arguments.of(POSITION_4_3, POSITION_4_2),
                Arguments.of(POSITION_4_3, POSITION_5_2),
                Arguments.of(POSITION_4_3, POSITION_5_3),
                Arguments.of(POSITION_5_3, POSITION_4_3),
                Arguments.of(POSITION_5_3, POSITION_6_3),
                Arguments.of(POSITION_5_3, POSITION_5_2),
                Arguments.of(POSITION_6_3, POSITION_5_3),
                Arguments.of(POSITION_6_3, POSITION_5_2),
                Arguments.of(POSITION_6_3, POSITION_6_2)
        );
    }

    public static Stream<Arguments> provideInvalidPositions() {
        return Stream.of(
                Arguments.of(POSITION_5_1, POSITION_4_2),
                Arguments.of(POSITION_5_1, POSITION_6_2),
                Arguments.of(POSITION_4_2, POSITION_5_1),
                Arguments.of(POSITION_6_2, POSITION_5_1),
                Arguments.of(POSITION_5_3, POSITION_6_2),
                Arguments.of(POSITION_5_3, POSITION_6_2),
                Arguments.of(POSITION_6_2, POSITION_5_3),
                Arguments.of(POSITION_6_2, POSITION_5_3)
        );
    }
}
