package janggi.domain.piece;

import janggi.domain.Country;
import janggi.domain.position.Position;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static janggi.test_util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;

class PoTest {

    @ParameterizedTest
    @MethodSource("provideValidPositions")
    void 포는_기물을_하나_뛰어넘어_이동한다(final Position newPosition) {
        // given
        final Piece piece = PieceFactory.createPo(Country.CHO, POSITION_5_5);

        // expected
        assertThatCode(() -> piece.move(newPosition, List.of(PieceFactory.createJol(POSITION_3_5)), List.of()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("provideValidPositions")
    void 포는_중간_기물이_없으면_뛰어넘을_수_없다(final Position newPosition) {
        // given
        final Piece piece = PieceFactory.createPo(Country.CHO, POSITION_5_5);

        // expected
        assertThatThrownBy(() -> piece.move(newPosition, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("provideValidPositions")
    void 포는_포를_뛰어넘을_수_없다(final Position newPosition) {
        // given
        final Piece piece = PieceFactory.createPo(Country.CHO, POSITION_5_5);

        // expected
        assertThatThrownBy(() -> piece.move(newPosition, List.of(PieceFactory.createPo(Country.CHO, POSITION_3_5)), List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("provideValidPositions")
    void 포는_기물을_하나_뛰어넘어_상대_기물을_먹을_수_있다(final Position newPosition) {
        // given
        final Piece piece = PieceFactory.createPo(Country.CHO, POSITION_5_5);

        // expected
        assertThatCode(() -> piece.move(newPosition, List.of(PieceFactory.createJol(POSITION_3_5)), List.of(PieceFactory.createJol(newPosition))))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("provideValidPositions")
    void 포는_포를_먹을_수_없다(final Position newPosition) {
        // given
        final Piece piece = PieceFactory.createPo(Country.CHO, POSITION_5_5);

        // expected
        assertThatThrownBy(() -> piece.move(newPosition, List.of(PieceFactory.createJol(POSITION_3_5)), List.of(PieceFactory.createPo(Country.HAN, newPosition))))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 포는_궁에서_대각선으로_움직일_수_있다() {
        // given
        final Piece piece = PieceFactory.createPo(Country.CHO, POSITION_6_1);

        // expected
        assertThatCode(() -> piece.move(POSITION_4_3, List.of(PieceFactory.createJol(POSITION_5_2)), List.of()))
                .doesNotThrowAnyException();
    }

    public static Stream<Arguments> provideValidPositions() {
        return Stream.of(
                Arguments.of(POSITION_2_5),
                Arguments.of(POSITION_1_5)
        );
    }
}