package janggi.domain.piece;

import static org.assertj.core.api.Assertions.assertThat;

import janggi.domain.Side;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class PieceFactoryTest {

    @ParameterizedTest
    @EnumSource(PieceType.class)
    void DB에서_가져온_PieceType과_Side를_전달하면_알맞은_기물_객체를_생성한다(PieceType pieceType) {
        Piece piece = PieceFactory.create(pieceType, Side.CHO);

        assertThat(piece.getType()).isEqualTo(pieceType);
        assertThat(piece.getSide()).isEqualTo(Side.CHO);
    }
}
