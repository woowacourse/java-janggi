package domain.piece;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PieceTest {
    Piece piece;

    @BeforeEach
    void setUp() {
        piece = Piece.of(Camp.CHO, PieceType.CHARIOT);
    }

    @Test
    void 같은_진영인지_반환한다() {
        assertThat(piece.isSameCamp(Piece.of(Camp.CHO, PieceType.ELEPHANT))).isTrue();
        assertThat(piece.isSameCamp(Piece.of(Camp.HAN, PieceType.SOLDIER))).isFalse();
    }

    @Test
    void 같은_타입의_기물인지_반환한다() {
        assertThat(piece.isSameType(PieceType.CHARIOT)).isTrue();
        assertThat(piece.isSameType(PieceType.ELEPHANT)).isFalse();
    }

    @Nested
    class 동등성_비교_테스트 {
        @Test
        void 같은_진영이고_같은_기물_타입이면_동등한_기물로_판단한다() {
            assertThat(piece.equals(Piece.of(Camp.CHO, PieceType.CHARIOT))).isTrue();
        }

        @Test
        void 진영이_다르면_다른_기물로_판단한다() {
            assertThat(piece.equals(Piece.of(Camp.HAN, PieceType.CHARIOT))).isFalse();
        }

        @Test
        void 기물_종류가_다르면_다른_기물로_판단한다() {
            assertThat(piece.equals(Piece.of(Camp.CHO, PieceType.HORSE))).isFalse();
        }

        @Test
        void 동등한_두_객체의_hashcode는_같다() {
            assertThat(piece.equals(Piece.of(Camp.CHO, PieceType.CHARIOT))).isTrue();
            assertThat(piece.hashCode()).isEqualTo(Piece.of(Camp.CHO, PieceType.CHARIOT).hashCode());
        }
    }
}
