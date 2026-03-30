package domain.board;

import static domain.util.AssertUtils.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.game.Side;
import domain.piece.Elephant;
import domain.piece.Horse;
import domain.piece.Piece;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class WingsTest {

    @Nested
    class 생성할_때_기물의_진영을_검증한다 {

        private static final String DIFFERENT_SIDE_MESSAGE = "진영에는 같은 소속의 기물만 배치할 수 있습니다";

        private final List<Piece> oppositeSidePieces = List.of(new Horse(Side.CHO), new Elephant(Side.HAN));
        private final List<Piece> owingSidePieces = List.of(new Horse(Side.CHO), new Elephant(Side.CHO));

        @Test
        void 좌진에_다른_진영의_기물이_있다면_예외를_던진다() {
            assertThatThrownBy(() -> new ChoWings(oppositeSidePieces, owingSidePieces))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(DIFFERENT_SIDE_MESSAGE);
        }

        @Test
        void 우진에_다른_진영의_기물이_있다면_예외를_던진다() {
            assertThatThrownBy(() -> new ChoWings(owingSidePieces, oppositeSidePieces))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(DIFFERENT_SIDE_MESSAGE);
        }

        @Test
        void 좌진과_우진에_다른_진영의_기물이_있다면_예외를_던진다() {
            assertThatThrownBy(() -> new ChoWings(oppositeSidePieces, oppositeSidePieces))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(DIFFERENT_SIDE_MESSAGE);
        }

        @Test
        void 좌진과_우진에_같은_진영의_기물만_있다면_정상적으로_생성된다() {
            assertThatNoException(() -> new ChoWings(owingSidePieces, owingSidePieces));
        }
    }
}
