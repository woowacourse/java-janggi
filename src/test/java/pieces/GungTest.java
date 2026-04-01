package pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import position.Position;

class GungTest {

    private static final Position DEFAULT = new Position(3, 3);

    @Nested
    @DisplayName("궁의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @Test
        void 앞_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            Position departure = DEFAULT;
            Position destination = departure.move(side.forwardDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 뒤_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            Position departure = DEFAULT;
            Position destination = departure.move(side.backDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            Position departure = DEFAULT;
            Position destination = departure.move(side.leftDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_이동할_수_있다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            Position departure = DEFAULT;
            Position destination = departure.move(side.rightDelta());
            // when & then
            assertThatCode(() -> piece.validateDestination(departure, destination))
                .doesNotThrowAnyException();
        }

        @Test
        void 앞_2칸이_도착지인_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            Position departure = DEFAULT;
            Position destination = departure
                .move(side.forwardDelta())
                .move(side.forwardDelta());
            // when & then
            assertThatThrownBy(() -> piece.validateDestination(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 대각선이_도착지인_경우_예외를_던진다() {
            // given
            Side side = Side.CHO;
            Piece piece = new Piece(side, PieceType.GUNG);
            Position departure = DEFAULT;
            Position destination = departure.move(side.rightForwardDelta());
            // when & then
            assertThatThrownBy(() -> piece.validateDestination(departure, destination))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 출발지와_도착지_사이에는_이동경로가_존재하지_않는다() {
        // given
        Side side = Side.CHO;
        Piece piece = new Piece(side, PieceType.GUNG);
        Position departure = DEFAULT;
        Position destination = departure.move(side.forwardDelta());
        // when
        List<Position> positions = piece.findPathPositions(departure, destination);
        // then
        assertThat(positions).hasSize(0);
    }
}