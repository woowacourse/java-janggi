package janggi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@ReplaceUnderBar
class PieceTest {

    private static final Side DEFAULT_SIDE = Side.HAN;
    private static final Position DEFAULT_POSITION = new Position(1, 2);

    @ParameterizedTest
    @EnumSource(value = Side.class)
    void 진영을_가진다(Side side) {
        Piece piece = new Piece(side, DEFAULT_POSITION);
        assertThat(piece.getSide()).isEqualTo(side);
    }

    @Test
    void 위치를_가진다() {
        Piece piece = new Piece(DEFAULT_SIDE, new Position(1, 2));

        assertThat(piece.getPosition()).isEqualTo(new Position(1, 2));
    }
}
