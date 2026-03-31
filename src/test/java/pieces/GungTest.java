package pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.movepolicy.MoveContext;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import domain.pieces.Gung;
import domain.pieces.Piece;
import domain.pieces.PieceType;
import domain.pieces.Side;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import domain.position.Position;

class GungTest {

    private static final Position DEFAULT = new Position(1, 1);

    @Nested
    @DisplayName("궁의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @Test
        void 상_1칸_이동할_수_있다() {
            // given
            Piece hanGung = new Gung(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp();
            // when & then
            assertThatCode(() -> hanGung.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 하_1칸_이동할_수_있다() {
            // given
            Piece hanGung = new Gung(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown();
            // when & then
            assertThatCode(() -> hanGung.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_이동할_수_있다() {
            // given
            Piece hanGung = new Gung(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft();
            // when & then
            assertThatCode(() -> hanGung.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_이동할_수_있다() {
            // given
            Piece hanGung = new Gung(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight();
            // when & then
            assertThatCode(() -> hanGung.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 상_2칸이_도착지인_경우_예외를_던진다() {
            // given
            Piece hanGung = new Gung(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveUp();
            // when & then
            assertThatThrownBy(() -> hanGung.askMoveContext(departure, destination))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 우상향이_도착지인_경우_예외를_던진다() {
            // given
            Piece hanGung = new Gung(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveRight();
            // when & then
            assertThatThrownBy(() -> hanGung.askMoveContext(departure, destination))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 출발지와_도착지_사이에는_이동경로가_존재하지_않는다() {
        // given
        Piece hanGung = new Gung(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveUp();
        // when
        MoveContext moveContext = hanGung.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathPositions()).hasSize(0);
    }

    @Test
    void 궁은_다른_진영의_기물만_공격할_수_있다() {
        // given
        Piece hanGung = new Gung(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveUp();
        // when
        MoveContext moveContext = hanGung.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.destinationRule())
                .isInstanceOf(BasicDestinationRule.class);
    }

    @Test
    void 궁은_이동_경로에_기물이_없을_때_이동할_수_있다() {
        // given
        Piece hanGung = new Gung(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveUp();
        // when
        MoveContext moveContext = hanGung.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathRule())
                .isInstanceOf(EmptyPathRule.class);
    }

    @Test
    void 궁은_본인의_식별자를_반환한다() {
        // given
        Piece gung = new Gung(Side.HAN);
        // when & then
        assertThat(gung.getType()).isEqualTo(PieceType.GUNG);
    }
}
