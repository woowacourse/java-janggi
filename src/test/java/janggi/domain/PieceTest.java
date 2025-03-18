package janggi.domain;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PieceTest {

    @ParameterizedTest
    @EnumSource(value = Side.class)
    void 진영을_가진다(Side side) {
        Piece piece = new Piece(side);
        assertThat(piece.getSide()).isEqualTo(side);
    }
}