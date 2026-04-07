package domain.pieces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.pieces.exception.InvalidMoveException;
import domain.pieces.exception.PieceErrorMessage;
import java.util.List;
import domain.movepolicy.MoveContext;
import domain.movepolicy.destination.PoDestinationRule;
import domain.movepolicy.path.PoPathRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import domain.position.Position;

class PoTest {

    private static final Position DEFAULT = new Position(3, 3);

    private Piece po() {
        return new Po(Side.CHO);
    }

    @Nested
    @DisplayName("포의 행마법")
    class PoMove {

        @Nested
        @DisplayName("이동 가능한 경우")
        class ValidMove {

            @Test
            void 왼쪽_경계의_포도_우로_여러_칸_이동할_수_있다() {
                // given
                Piece po = po();
                Position departure = new Position(3, 0);
                Position destination = departure.moveRight().moveRight();
                // when & then
                assertThatCode(() -> po.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 오른쪽_경계의_포도_좌로_여러_칸_이동할_수_있다() {
                // given
                Piece po = po();
                Position departure = new Position(3, 8);
                Position destination = departure.moveLeft().moveLeft();
                // when & then
                assertThatCode(() -> po.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 상으로_여러_칸_이동할_수_있다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveUp().moveUp();
                // when & then
                assertThatCode(() -> po.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 하로_여러_칸_이동할_수_있다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveDown().moveDown();
                // when & then
                assertThatCode(() -> po.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 좌로_여러_칸_이동할_수_있다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveLeft().moveLeft();
                // when & then
                assertThatCode(() -> po.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }

            @Test
            void 우로_여러_칸_이동할_수_있다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveRight().moveRight();
                // when & then
                assertThatCode(() -> po.askMoveContext(departure, destination))
                        .doesNotThrowAnyException();
            }
        }

        @Nested
        @DisplayName("이동 불가능한 경우")
        class InvalidMove {

            @Test
            void 왼쪽_경계의_포도_제자리_이동이면_행마법_예외를_던진다() {
                // given
                Piece po = po();
                Position departure = new Position(3, 0);
                Position destination = departure;
                // when & then
                assertThatThrownBy(() -> po.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.PO_INVALID_MOVE.message());
            }

            @Test
            void 오른쪽_경계의_포도_제자리_이동이면_행마법_예외를_던진다() {
                // given
                Piece po = po();
                Position departure = new Position(3, 8);
                Position destination = departure;
                // when & then
                assertThatThrownBy(() -> po.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.PO_INVALID_MOVE.message());
            }

            @Test
            void 궁성_밖에서_우상향이_도착지인_경우_예외를_던진다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveRightUp();
                // when & then
                assertThatThrownBy(() -> po.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.PO_INVALID_MOVE.message());
            }

            @Test
            void 상_1칸_차이가_도착지인_경우_예외를_던진다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveUp();
                // when & then
                assertThatThrownBy(() -> po.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.PO_ONE_SPACE_MOVE.message());
            }

            @Test
            void 하_1칸_차이가_도착지인_경우_예외를_던진다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveDown();
                // when & then
                assertThatThrownBy(() -> po.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.PO_ONE_SPACE_MOVE.message());
            }

            @Test
            void 좌_1칸_차이가_도착지인_경우_예외를_던진다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveLeft();
                // when & then
                assertThatThrownBy(() -> po.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.PO_ONE_SPACE_MOVE.message());
            }

            @Test
            void 우_1칸_차이가_도착지인_경우_예외를_던진다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveRight();
                // when & then
                assertThatThrownBy(() -> po.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.PO_ONE_SPACE_MOVE.message());
            }

            @Test
            void 궁성_대각선_선분_한_칸_이동이면_한_칸_이동_예외를_던진다() {
                Piece po = po();
                Position departure = new Position(0, 3);
                Position destination = departure.moveRightUp();

                assertThatThrownBy(() -> po.askMoveContext(departure, destination))
                        .isInstanceOf(InvalidMoveException.class)
                        .hasMessage(PieceErrorMessage.PO_ONE_SPACE_MOVE.message());
            }

        }
    }

    @Nested
    @DisplayName("포의 이동 경로")
    class PathPosition {

        @Nested
        @DisplayName("이동 경로를 반환하는 경우")
        class ExistingPath {

            @Test
            void 출발지와_도착지_차이_만큼의_이동_경로가_존재한다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveDown().moveDown();
                // when
                MoveContext moveContext = po.askMoveContext(departure, destination);
                // then
                int gapSize = departure.row() - destination.row() - 1;
                assertThat(moveContext.pathPositions()).hasSize(gapSize);
            }

            @Test
            void 상으로_여러_칸_차이인_경우의_이동_경로를_반환한다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveUp().moveUp().moveUp();
                // when
                MoveContext moveContext = po.askMoveContext(departure, destination);
                // then
                List<Position> pathPositions = moveContext.pathPositions();
                assertThat(pathPositions.get(0)).isEqualTo(departure.moveUp());
                assertThat(pathPositions.get(1)).isEqualTo(departure.moveUp().moveUp());
            }

            @Test
            void 하로_여러_칸_차이인_경우의_이동_경로를_반환한다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveDown().moveDown().moveDown();
                // when
                MoveContext moveContext = po.askMoveContext(departure, destination);
                // then
                List<Position> pathPositions = moveContext.pathPositions();
                assertThat(pathPositions.get(0)).isEqualTo(departure.moveDown());
                assertThat(pathPositions.get(1)).isEqualTo(departure.moveDown().moveDown());
            }

            @Test
            void 좌로_여러_칸_차이인_경우의_이동_경로를_반환한다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveLeft().moveLeft().moveLeft();
                // when
                MoveContext moveContext = po.askMoveContext(departure, destination);
                // then
                List<Position> pathPositions = moveContext.pathPositions();
                assertThat(pathPositions.get(0)).isEqualTo(departure.moveLeft());
                assertThat(pathPositions.get(1)).isEqualTo(departure.moveLeft().moveLeft());
            }

            @Test
            void 우로_여러_칸_차이인_경우의_이동_경로를_반환한다() {
                // given
                Piece po = po();
                Position departure = DEFAULT;
                Position destination = departure.moveRight().moveRight().moveRight();
                // when
                MoveContext moveContext = po.askMoveContext(departure, destination);
                // then
                List<Position> pathPositions = moveContext.pathPositions();
                assertThat(pathPositions.get(0)).isEqualTo(departure.moveRight());
                assertThat(pathPositions.get(1)).isEqualTo(departure.moveRight().moveRight());
            }
        }
    }

    @Nested
    @DisplayName("포의 특수 규칙")
    class SpecialRule {

        @Test
        void 포의_이동은_PoDestinationRule을_사용한다() {
            // given
            Piece po = po();
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveUp();
            // when
            MoveContext moveContext = po.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.destinationRule())
                    .isInstanceOf(PoDestinationRule.class);
        }

        @Test
        void 포의_이동은_PoPathRule을_사용한다() {
            // given
            Piece po = po();
            Position departure = DEFAULT;
            Position destination = departure.moveUp().moveUp();
            // when
            MoveContext moveContext = po.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.pathRule())
                    .isInstanceOf(PoPathRule.class);
        }

        @Test
        void 궁성_대각선_이동시_PoDestinationRule을_사용한다() {
            // given
            Piece po = po();
            Position departure = new Position(0, 3);
            Position destination = departure.moveRightUp().moveRightUp();
            // when
            MoveContext moveContext = po.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.destinationRule())
                    .isInstanceOf(PoDestinationRule.class);
        }

        @Test
        void 궁성_대각선_이동시_PoPathRule을_사용한다() {
            // given
            Piece po = po();
            Position departure = new Position(0, 3);
            Position destination = departure.moveRightUp().moveRightUp();
            // when
            MoveContext moveContext = po.askMoveContext(departure, destination);
            // then
            assertThat(moveContext.pathRule())
                    .isInstanceOf(PoPathRule.class);
        }
    }

    @Test
    void 포는_본인의_식별자를_반환한다() {
        // given
        Piece po = po();
        // when & then
        assertThat(po.getType()).isEqualTo(PieceType.PO);
    }
}
