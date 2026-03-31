package pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.pieces.Piece;
import domain.pieces.PieceType;
import domain.pieces.Sang;
import domain.pieces.Side;
import java.util.List;
import domain.movepolicy.MoveContext;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import domain.position.Position;

class SangTest {

    private static final Position DEFAULT = new Position(3, 3);

    @Nested
    @DisplayName("상의 행마법 기준으로 도착지에 이동 가능한지 검증한다")
    class CanMove {

        @Test
        void 상_1칸_우상향_2칸_이동할_수_있다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveRightUp().moveRightUp();
            // when & then
            assertThatCode(() -> sang.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 상_1칸_좌상향_2칸_이동할_수_있다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveLeftUp().moveLeftUp();
            // when & then
            assertThatCode(() -> sang.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 하_1칸_우하향_2칸_이동할_수_있다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown().moveRightDown().moveRightDown();
            // when & then
            assertThatCode(() -> sang.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 하_1칸_좌하향_2칸_이동할_수_있다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown().moveLeftDown().moveLeftDown();
            // when & then
            assertThatCode(() -> sang.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_좌상향_2칸_이동할_수_있다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft().moveLeftUp().moveLeftUp();
            // when & then
            assertThatCode(() -> sang.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 좌_1칸_좌하향_2칸_이동할_수_있다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft().moveLeftDown().moveLeftDown();
            // when & then
            assertThatCode(() -> sang.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_우상향_2칸_이동할_수_있다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight().moveRightUp().moveRightUp();
            // when & then
            assertThatCode(() -> sang.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 우_1칸_우하향_2칸_이동할_수_있다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight().moveRightDown().moveRightDown();
            // when & then
            assertThatCode(() -> sang.askMoveContext(departure, destination))
                    .doesNotThrowAnyException();
        }

        @Test
        void 상_2칸이_도착지인_경우_예외를_던진다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveUp();
            // when & then
            assertThatThrownBy(() -> sang.askMoveContext(departure, destination))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 우상향이_도착지인_경우_예외를_던진다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRightUp();
            // when & then
            assertThatThrownBy(() -> sang.askMoveContext(departure, destination))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Nested
    @DisplayName("상의 이동 경로를 검증한다")
    class PathPosition {

        @Test
        void 출발지와_도착지_사이에는_두_칸의_이동_경로만_존재한다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveRightUp().moveRightUp();
            // when
            MoveContext moveContext = sang.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.pathPositions()).hasSize(2);
        }

        @Test
        void 상_1칸_우상향_2칸_이동의_경로를_반환한다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveRightUp().moveRightUp();
            // when
            MoveContext moveContext = sang.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveUp());
            assertThat(pathPosition.get(1)).isEqualTo(departure.moveUp().moveRightUp());
        }

        @Test
        void 상_1칸_좌상향_2칸_이동의_경로를_반환한다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveLeftUp().moveLeftUp();
            // when
            MoveContext moveContext = sang.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveUp());
            assertThat(pathPosition.get(1)).isEqualTo(departure.moveUp().moveLeftUp());
        }

        @Test
        void 하_1칸_우하향_2칸_이동의_경로를_반환한다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown().moveRightDown().moveRightDown();
            // when
            MoveContext moveContext = sang.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveDown());
            assertThat(pathPosition.get(1)).isEqualTo(departure.moveDown().moveRightDown());
        }

        @Test
        void 하_1칸_좌하향_2칸_이동의_경로를_반환한다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveDown().moveLeftDown().moveLeftDown();
            // when
            MoveContext moveContext = sang.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveDown());
            assertThat(pathPosition.get(1)).isEqualTo(departure.moveDown().moveLeftDown());
        }

        @Test
        void 좌_1칸_좌상향_2칸_이동의_경로를_반환한다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft().moveLeftUp().moveLeftUp();
            // when
            MoveContext moveContext = sang.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveLeft());
            assertThat(pathPosition.get(1)).isEqualTo(departure.moveLeft().moveLeftUp());
        }

        @Test
        void 좌_1칸_좌하향_2칸_이동의_경로를_반환한다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveLeft().moveLeftDown().moveLeftDown();
            // when
            MoveContext moveContext = sang.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveLeft());
            assertThat(pathPosition.get(1)).isEqualTo(departure.moveLeft().moveLeftDown());
        }

        @Test
        void 우_1칸_우상향_2칸_이동의_경로를_반환한다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight().moveRightUp().moveRightUp();
            // when
            MoveContext moveContext = sang.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveRight());
            assertThat(pathPosition.get(1)).isEqualTo(departure.moveRight().moveRightUp());
        }

        @Test
        void 우_1칸_우하향_2칸_이동의_경로를_반환한다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight().moveRightDown().moveRightDown();
            // when
            MoveContext moveContext = sang.askMoveContext(departure, destination);
            // then
            List<Position> pathPosition = moveContext.pathPositions();
            assertThat(pathPosition.get(0)).isEqualTo(departure.moveRight());
            assertThat(pathPosition.get(1)).isEqualTo(departure.moveRight().moveRightDown());
        }

        @Test
        void 도착지가_유효하지_않은_경우_예외를_던진다() {
            // given
            Piece sang = new Sang(Side.HAN);
            Position departure = DEFAULT;
            Position destination = departure.moveRight().moveRight();
            // when & then
            assertThatThrownBy(() -> sang.askMoveContext(departure, destination))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @Test
    void 상은_다른_진영의_기물만_공격할_수_있다() {
        // given
        Piece sang = new Sang(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveUp().moveRightUp().moveRightUp();
        // when
        MoveContext moveContext = sang.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.destinationRule())
                .isInstanceOf(BasicDestinationRule.class);
    }

    @Test
    void 상은_이동_경로에_기물이_없을_때_이동할_수_있다() {
        // given
        Piece sang = new Sang(Side.HAN);
        Position departure = DEFAULT;
        Position destination = departure.moveUp().moveRightUp().moveRightUp();
        // when
        MoveContext moveContext = sang.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathRule())
                .isInstanceOf(EmptyPathRule.class);
    }

    @Test
    void 상은_본인의_식별자를_반환한다() {
        // given
        Piece sang = new Sang(Side.HAN);
        // when & then
        assertThat(sang.getType()).isEqualTo(PieceType.SANG);
    }
}
