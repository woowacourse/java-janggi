package janggi.domain.piece.impl;

import janggi.domain.piece.Gung;
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
        final Po po = new Po(POSITION_5_5, new Gung());

        // expected
        assertThatCode(() -> po.move(newPosition, List.of(new Ma(POSITION_3_5)), List.of()))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("provideValidPositions")
    void 포는_중간_기물이_없으면_뛰어넘을_수_없다(final Position newPosition) {
        // given
        final Po po = new Po(POSITION_5_5, new Gung());

        // expected
        assertThatThrownBy(() -> po.move(newPosition, List.of(), List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("provideValidPositions")
    void 포는_기물을_하나_뛰어넘어_상대_기물을_먹을_수_있다(final Position newPosition) {
        // given
        final Po po = new Po(POSITION_5_5, new Gung());

        // expected
        assertThatCode(() -> po.move(newPosition, List.of(new Ma(POSITION_3_5)), List.of(new Ma(newPosition))))
                .doesNotThrowAnyException();
    }

    @Test
    void 포는_궁에서_대각선으로_움직일_수_있다() {
        // given
        final Po po = new Po(POSITION_6_1, new Gung());

        // expected
        assertThatCode(() -> po.move(POSITION_4_3, List.of(new Jang(POSITION_5_2, new Gung())), List.of()))
                .doesNotThrowAnyException();
    }

    public static Stream<Arguments> provideValidPositions() {
        return Stream.of(
                Arguments.of(POSITION_2_5),
                Arguments.of(POSITION_1_5)
        );
    }
}