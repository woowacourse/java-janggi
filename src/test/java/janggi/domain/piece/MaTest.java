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

class MaTest extends BaseTest {

    @ParameterizedTest
    @MethodSource("provideValidPositions")
    void 마는_전진_대각선으로_움직인다(final Position newPosition) {
        // given
        final Piece piece = new Piece(PieceType.MA, POSITION_5_5);

        // expected
        assertThatCode(() -> piece.move(newPosition, List.of(), List.of()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPositions")
    void 그_외의_위치로는_이동할_수_없다(final Position invalidPosition) {
        // given
        final Piece piece = new Piece(PieceType.MA, POSITION_5_5);

        // expected
        assertThatThrownBy(() -> piece.move(invalidPosition, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("provideValidPositions")
    void 중간에_기물로_가로막힌다(final Position newPosition) {
        // given
        final Piece piece = new Piece(PieceType.MA, POSITION_5_5);

        // expected
        assertThatThrownBy(() -> piece.move(newPosition, List.of(
                new Piece(PieceType.MA, POSITION_4_5),
                new Piece(PieceType.MA, POSITION_6_5),
                new Piece(PieceType.MA, POSITION_5_4),
                new Piece(PieceType.MA, POSITION_5_6)
        ), List.of())).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("provideValidPositions")
    void 마지막에_적_기물이_있으면_먹는다(final Position newPosition) {
        // given
        final Piece piece = new Piece(PieceType.MA, POSITION_5_5);

        // expected
        assertThatCode(() -> piece.move(newPosition, List.of(), List.of(new Piece(PieceType.MA, newPosition))))
                .doesNotThrowAnyException();
    }

    public static Stream<Arguments> provideValidPositions() {
        return Stream.of(
                Arguments.of(POSITION_3_4),
                Arguments.of(POSITION_3_6),
                Arguments.of(POSITION_4_3),
                Arguments.of(POSITION_4_7),
                Arguments.of(POSITION_6_3),
                Arguments.of(POSITION_6_7),
                Arguments.of(POSITION_7_4),
                Arguments.of(POSITION_7_6)
        );
    }

    public static Stream<Arguments> provideInvalidPositions() {
        return Stream.of(
                Arguments.of(POSITION_3_1),
                Arguments.of(POSITION_3_2),
                Arguments.of(POSITION_3_3),
                Arguments.of(POSITION_3_5),
                Arguments.of(POSITION_3_7),
                Arguments.of(POSITION_3_8),
                Arguments.of(POSITION_4_1),
                Arguments.of(POSITION_4_2),
                Arguments.of(POSITION_4_4),
                Arguments.of(POSITION_4_5),
                Arguments.of(POSITION_4_6),
                Arguments.of(POSITION_4_8),
                Arguments.of(POSITION_5_1),
                Arguments.of(POSITION_5_2),
                Arguments.of(POSITION_5_3),
                Arguments.of(POSITION_5_4),
                Arguments.of(POSITION_5_5),
                Arguments.of(POSITION_5_6),
                Arguments.of(POSITION_5_7),
                Arguments.of(POSITION_5_8),
                Arguments.of(POSITION_6_1),
                Arguments.of(POSITION_6_2),
                Arguments.of(POSITION_6_4),
                Arguments.of(POSITION_6_5),
                Arguments.of(POSITION_6_6),
                Arguments.of(POSITION_6_8)
        );
    }
}
