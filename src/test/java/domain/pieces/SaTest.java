package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.pieces.exception.InvalidMoveException;
import domain.pieces.exception.PieceErrorMessage;
import domain.movepolicy.MoveContext;
import domain.movepolicy.destination.BasicDestinationRule;
import domain.movepolicy.path.EmptyPathRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import domain.position.Position;

class SaTest {

    private static final Position DEFAULT = new Position(1, 4);
    private Piece sa() {
        return new Sa(Side.HAN);
    }

    @Nested
    @DisplayName("사의 행마법")
    class SaMove {

        @Nested
        @DisplayName("이동 가능한 경우")
        class ValidMove {

            @Test
            void 상_1칸_이동할_수_있다() {
                // given
                Piece sa = sa();
                Position departure = DEFAULT;
                Position destination = departure.moveUp();
                // when & then
                assertThatCode(() -> sa.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 하_1칸_이동할_수_있다() {
                // given
                Piece sa = sa();
                Position departure = DEFAULT;
                Position destination = departure.moveDown();
                // when & then
                assertThatCode(() -> sa.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 좌_1칸_이동할_수_있다() {
                // given
                Piece sa = sa();
                Position departure = DEFAULT;
                Position destination = departure.moveLeft();
                // when & then
                assertThatCode(() -> sa.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 우_1칸_이동할_수_있다() {
                // given
                Piece sa = sa();
                Position departure = DEFAULT;
                Position destination = departure.moveRight();
                // when & then
                assertThatCode(() -> sa.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 궁성_내부에서_우상향으로_한_칸_이동할_수_있다() {
                // given
                Piece sa = sa();
                Position departure = new Position(0, 3);
                Position destination = departure.moveRightUp();
                // when & then
                assertThatCode(() -> sa.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 궁성_내부에서_좌상향으로_한_칸_이동할_수_있다() {
                // given
                Piece sa = sa();
                Position departure = new Position(0, 5);
                Position destination = departure.moveLeftUp();
                // when & then
                assertThatCode(() -> sa.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("이동 불가능한 경우")
        class InvalidMove {

            @Test
            void 사의_제자리_이동은_예외를_던진다() {
                // given
                Piece sa = sa();
                Position departure = DEFAULT;
                Position destination = departure;
                // when & then
                assertThatThrownBy(() -> sa.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.SA_INVALID_MOVE.message());
            }

            @Test
            void 맨_아래_행의_사도_제자리_이동이면_예외를_던진다() {
                // given
                Piece sa = sa();
                Position departure = new Position(0, 3);
                Position destination = departure;
                // when & then
                assertThatThrownBy(() -> sa.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.SA_INVALID_MOVE.message());
            }

            @Test
            void 맨_위_행의_사도_제자리_이동이면_예외를_던진다() {
                // given
                Piece sa = sa();
                Position departure = new Position(9, 3);
                Position destination = departure;
                // when & then
                assertThatThrownBy(() -> sa.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.SA_INVALID_MOVE.message());
            }

            @Test
            void 상_2칸이_도착지인_경우_예외를_던진다() {
                // given
                Piece sa = sa();
                Position departure = DEFAULT;
                Position destination = departure.moveUp().moveUp();
                // when & then
                assertThatThrownBy(() -> sa.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.SA_INVALID_MOVE.message());
            }

            @Test
            void 궁성_밖_대각선이_도착지인_경우_예외를_던진다() {
                // given
                Piece sa = sa();
                Position departure = new Position(1, 3);
                Position destination = departure.moveLeftUp();
                // when & then
                assertThatThrownBy(() -> sa.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.SA_INVALID_MOVE.message());
            }

            @Test
            void 궁성_밖_오른쪽이_도착지인_경우_예외를_던진다() {
                // given
                Piece sa = sa();
                Position departure = new Position(1, 5);
                Position destination = departure.moveRight();
                // when & then
                assertThatThrownBy(() -> sa.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.SA_INVALID_MOVE.message());
            }
        }
    }

    @Test
    void 출발지와_도착지_사이에는_이동경로가_존재하지_않는다() {
        // given
        Piece sa = sa();
        Position departure = DEFAULT;
        Position destination = departure.moveUp();
        // when
        MoveContext moveContext = sa.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathPositions()).hasSize(0);
    }

    @Test
    void 사는_다른_진영의_기물만_공격할_수_있다() {
        // given
        Piece sa = sa();
        Position departure = DEFAULT;
        Position destination = departure.moveUp();
        // when
        MoveContext moveContext = sa.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.destinationRule())
                .isInstanceOf(BasicDestinationRule.class);
    }

    @Test
    void 사는_이동_경로에_기물이_없을_때_이동할_수_있다() {
        // given
        Piece sa = sa();
        Position departure = DEFAULT;
        Position destination = departure.moveUp();
        // when
        MoveContext moveContext = sa.askMoveContext(departure, destination);
        // then
        assertThat(moveContext.pathRule())
                .isInstanceOf(EmptyPathRule.class);
    }

    @Test
    void 사는_본인의_식별자를_반환한다() {
        // given
        Piece sa = sa();
        // when & then
        assertThat(sa.getType()).isEqualTo(PieceType.SA);
    }
}
